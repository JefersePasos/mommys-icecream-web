package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.service.CarritoService;
import MommysIceCreamWeb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String mostrarCarrito(Model model) {
        model.addAttribute("items", carritoService.getItems());
        model.addAttribute("total", carritoService.calcularTotal());
        return "carrito/carrito";
    }

    @PostMapping("/agregar")
    public String agregarProducto(@RequestParam Long productoId, @RequestParam int cantidad) {
        var productoOpt = productoService.obtenerPorId(productoId);
        productoOpt.ifPresent(producto -> carritoService.agregarProducto(producto, cantidad));
        return "redirect:/carrito"; // Redirige al carrito después de agregar el producto, cambiar despues
    }

    @PostMapping("/eliminar")
    public String eliminarProducto(@RequestParam Long productoId) {
        carritoService.eliminarProducto(productoId);
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito() {
        carritoService.vaciar();
        return "redirect:/carrito";
    }

    @PostMapping("/aumentar")
    public String aumentarCantidad(@RequestParam Long productoId) {
        carritoService.aumentarCantidad(productoId);
        return "redirect:/carrito";
    }

    @PostMapping("/disminuir")
    public String disminuirCantidad(@RequestParam Long productoId) {
        carritoService.disminuirCantidad(productoId);
        return "redirect:/carrito";
    }
}