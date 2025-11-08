package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String inicio(Model model) {
        // Mostrar 3 productos más recientes disponibles para la portada
        model.addAttribute("recientes", productoService.listar3RecientesDisponibles());
        return "index";
    }
    
    
     // Redirige la ruta corta /catalogo al controlador de productos.
    @GetMapping("/catalogo")
    public String catalogoRedirect() {
        return "redirect:/productos/catalogo";
    }
}
