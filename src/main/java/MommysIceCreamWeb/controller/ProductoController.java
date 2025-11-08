package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.domain.Sugerencia;
import MommysIceCreamWeb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping ("/dashboardProductos")
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/dashboardProductos";
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        var productos = productoService.listarTodos();
        var recientes = productoService.listar3RecientesDisponibles();
        model.addAttribute("productos", productos);
        model.addAttribute("recientes", recientes);
        model.addAttribute("sugerencia", new Sugerencia());
        return "productos/catalogo";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/agregar_producto";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos/dashboardProductos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        productoService.obtenerPorId(id).ifPresent(p -> model.addAttribute("producto", p));
        return "productos/agregar_producto";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/productos/dashboardProductos";
    }
    
    // Confirmación: permitir acceso directo por GET
    @GetMapping("/confirmacion")
    public String verConfirmacion() {
        return "productos/confirmacion";
    }

    // Confirmación: recibir POST desde el formulario de sugerencias
    @PostMapping("/confirmacion")
    public String recibirSugerencia(@ModelAttribute("sugerencia") Sugerencia sugerencia) {
        // Aquí podrías persistir la sugerencia o enviar una notificación
        return "productos/confirmacion"; // o redirect:/productos/confirmacion para PRG
    }
    @ModelAttribute("sugerencia") 
    public Sugerencia initSugerencia(){ 
        return new Sugerencia();
     }
}
