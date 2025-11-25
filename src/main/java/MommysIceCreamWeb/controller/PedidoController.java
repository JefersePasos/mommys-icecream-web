package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.service.CarritoService;
import MommysIceCreamWeb.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comprar")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/compras/historial")
    public String historialPedidos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            var pedidos = pedidoService.obtenerPedidosPorUsuario(usuario);
            model.addAttribute("pedidos", pedidos);
            return "compras/historial_compras";
        }
        return "redirect:/login";
    }

    @PostMapping
    public String procesarCompra() {
        pedidoService.realizarPedido(carritoService.getItems());
        carritoService.vaciar(); // Limpia el carrito
        return "compras/confirmacion";
    }
    
}