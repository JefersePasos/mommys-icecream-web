package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.CarritoItem;
import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        List<CarritoItem> carrito = obtenerCarrito(session);
        model.addAttribute("carrito", carrito);
        return "carrito/ver";
    }

    @PostMapping("/agregar/{id}")
    public String agregarAlCarrito(@PathVariable Long id, HttpSession session) {
        Producto producto = productoService.obtenerPorId(id).orElse(null);
        if (producto == null) return "redirect:/productos/catalogo";

        List<CarritoItem> carrito = obtenerCarrito(session);
        for (CarritoItem item : carrito) {
            if (item.getProducto().getId().equals(id)) {
                item.setCantidad(item.getCantidad() + 1);
                session.setAttribute("carrito", carrito);
                return "redirect:/carrito";
            }
        }

        carrito.add(new CarritoItem(producto, 1));
        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }

    @PostMapping("/remover/{id}")
    public String removerDelCarrito(@PathVariable Long id, HttpSession session) {
        List<CarritoItem> carrito = obtenerCarrito(session);
        carrito.removeIf(item -> item.getProducto().getId().equals(id));
        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }

    private List<CarritoItem> obtenerCarrito(HttpSession session) {
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }
}