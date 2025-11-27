package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Pedido;
import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.service.CarritoService;
import MommysIceCreamWeb.service.PdfService;
import MommysIceCreamWeb.service.PedidoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/compras")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/historial")
    public String historialPedidos(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @RequestParam(required = false) String desde,
        @RequestParam(required = false) String hasta,
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) Integer meses,
        Model model,
        HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        Page<Pedido> paginaPedidos = pedidoService.obtenerPedidosPaginados(
            usuario,
            page == null ? 0 : page,     // página actual
            size == null ? 5 : size      // tamaño por defecto (5 filas)
        );

        List<Pedido> pedidos = new java.util.ArrayList<>(paginaPedidos.getContent());

        //  FILTRO POR FECHAS
        if (desde != null && !desde.isEmpty()) {
            var desdeDate = LocalDate.parse(desde).atStartOfDay();
            pedidos.removeIf(p -> p.getCreated_at().isBefore(desdeDate));
        }

        if (hasta != null && !hasta.isEmpty()) {
            var hastaDate = LocalDate.parse(hasta).atTime(23, 59, 59);
            pedidos.removeIf(p -> p.getCreated_at().isAfter(hastaDate));
        }

        //  FILTRO POR ESTADO
        if (estado != null && !estado.isEmpty()) {
            boolean estadoBool = Boolean.parseBoolean(estado);
            pedidos.removeIf(p -> p.isOrderStatus() != estadoBool);
        }

        //  FILTRO POR MESES
        if (meses != null && meses > 0) {
            LocalDateTime limite = LocalDateTime.now().minusMonths(meses);
            pedidos.removeIf(p -> p.getCreated_at().isBefore(limite));
        }

        // Enviar pedidos a la vista
        model.addAttribute("pagina", paginaPedidos);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        model.addAttribute("estado", estado);
        model.addAttribute("meses", meses);
        
        return "compras/historial_compras";
    }

    @GetMapping("/pedidos/detalle/{id}")
    public String verDetallePedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.findById(id); // o tu método equivalente
        
        model.addAttribute("pedido", pedido);
        return "compras/detalle_pedido";
    }

    @GetMapping("/pedidos/pdf/{id}")
    public void generarFacturaPDF(@PathVariable Long id, HttpServletResponse response) throws Exception {

        Pedido pedido = pedidoService.findById(id);

        if (pedido == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pedido no encontrado");
            return;
        }

        pdfService.generarFacturaPDF(pedido, response);
    }

    @PostMapping
    public String procesarCompra() {
        pedidoService.realizarPedido(carritoService.getItems());
        carritoService.vaciar(); // Limpia el carrito
        return "compras/confirmacion";
    }
    
}