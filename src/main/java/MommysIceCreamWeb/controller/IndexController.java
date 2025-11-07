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
        // 3 productos más recientes disponibles para la portada
        model.addAttribute("recientes", productoService.listar3RecientesDisponibles());
        return "index";
    }
    
    /**
     * Redirige la ruta corta /catalogo al controlador de productos.
     * Esto evita el 404 cuando alguien visita /catalogo directamente.
     */
    @GetMapping("/catalogo")
    public String catalogoRedirect() {
        return "redirect:/productos/catalogo";
    }
    
//    @GetMapping("/catalogo")
//    public String cargarCatalogo (Model model) {
//        model.addAttribute("idCategoriaActual", idCategoria);
//        var categoriaOptional = categoriaServices.getCategoria(idCategoria);
//        if (categoriaOptional.isEmpty()) {
//            //Puede ser que no se exista la categoria buscada...
//            model.addAttribute("productos", java.util.Collections.emptyList());
//        } else {
//            var categoria = categoriaOptional.get();
//            var productos = categoria.getProductos();
//            model.addAttribute("productos", productos);
//        }
//        var categorias = categoriaServices.getCategorias(true);
//        model.addAttribute("categorias", categorias);
//        return "/index";
//    }
}
