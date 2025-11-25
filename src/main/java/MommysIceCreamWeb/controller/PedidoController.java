package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Pedido;
import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.service.CarritoService;
import MommysIceCreamWeb.service.PdfService;
import MommysIceCreamWeb.service.PedidoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String historialPedidos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            var pedidos = pedidoService.obtenerPedidosPorUsuario(usuario);

            // Asegura que productos nunca sea null
            pedidos.forEach(pedido -> {
                if (pedido.getProductos() == null) {
                    pedido.setProductos(java.util.Collections.emptyList());
                }
            });

            model.addAttribute("pedidos", pedidos);
            return "compras/historial_compras";
        }
        return "redirect:/login";
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