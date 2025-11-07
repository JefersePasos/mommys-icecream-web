package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    private final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping ("/dashboardProductos")
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/dashboardProductos";
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        // Muestra solo productos disponibles
        var productos = productoService.listarDisponibles();
        logger.info("Cargando catálogo: {} productos encontrados", productos == null ? 0 : productos.size());
        model.addAttribute("productos", productos);
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
}
