package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/reviews")
public class AdminReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String listarPendientes(Model model) {
        var pendientes = reviewService.obtenerPendientes();
        model.addAttribute("pendientes", pendientes);
        return "admin/reviews_dashboard";
    }

    @GetMapping("/aprobar/{id}")
    public String aprobar(@PathVariable Long id) {
        reviewService.aprobar(id);
        return "redirect:/admin/reviews";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        reviewService.eliminar(id);
        return "redirect:/admin/reviews";
    }
}
