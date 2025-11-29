package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // ⭐ MOSTRAR RESEÑAS PENDIENTES
    @GetMapping("/pendientes")
    public String listarPendientes(Model model) {
        var reviews = reviewService.obtenerPendientes();
        model.addAttribute("reviews", reviews);
        return "reviews/pendientes";
    }

    // ⭐ APROBAR RESEÑA
    @PostMapping("/aprobar/{id}")
    public String aprobar(@PathVariable Long id) {
        reviewService.aprobar(id);
        return "redirect:/reviews/pendientes";
    }

    // ⭐ ELIMINAR RESEÑA
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        reviewService.eliminar(id);
        return "redirect:/reviews/pendientes";
    }
}
