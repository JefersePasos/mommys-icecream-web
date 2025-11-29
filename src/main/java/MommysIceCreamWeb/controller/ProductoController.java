package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.domain.Sugerencia;
import MommysIceCreamWeb.domain.Review;
import MommysIceCreamWeb.service.ProductoService;
import MommysIceCreamWeb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/dashboardProductos")
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

    @GetMapping("/confirmacion")
    public String verConfirmacion() {
        return "productos/confirmacion";
    }

    @PostMapping("/confirmacion")
    public String recibirSugerencia(@ModelAttribute("sugerencia") Sugerencia sugerencia) {
        return "productos/confirmacion";
    }

    @ModelAttribute("sugerencia")
    public Sugerencia initSugerencia() {
        return new Sugerencia();
    }

    // ⭐ DETALLE DEL PRODUCTO (con reviews incluidas)
    @GetMapping("/detalle/{id}")
    public String detalleProducto(@PathVariable Long id, Model model) {

        var productoOpt = productoService.obtenerPorId(id);

        if (productoOpt.isEmpty()) {
            return "redirect:/productos/catalogo";
        }

        var producto = productoOpt.get();
        model.addAttribute("producto", producto);

        var reviews = reviewService.obtenerReviewsAprobadas(producto);
        model.addAttribute("reviews", reviews);

        return "productos/detalle_producto";
    }

    // ⭐ GUARDAR REVIEW DE UN PRODUCTO
    @PostMapping("/detalle/{id}/review")
    public String guardarReview(
            @PathVariable Long id,
            @RequestParam int rating,
            @RequestParam String comment) {

        var productoOpt = productoService.obtenerPorId(id);
        if (productoOpt.isEmpty()) {
            return "redirect:/productos/catalogo";
        }

        var producto = productoOpt.get();

        Review review = new Review();
        review.setProducto(producto);
        review.setRating(rating);
        review.setComment(comment);
        review.setApproved(false); // Pendiente de aprobación

        reviewService.guardarReview(review);

        return "redirect:/productos/detalle/" + id;
    }
}
