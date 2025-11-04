package MommysIceCreamWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    
//    // Las últimas versiones de Spring, recomiendan utilziar final y contructor en lugar de @autowired
//    private final ProductoServices productoServices;
//    private final CategoriaServices categoriaServices;
//    
//    // (Spring inyecta automáticamente)
//    public IndexController(ProductoServices productoServices, CategoriaServices categoriaServices) {
//        this.productoServices = productoServices;
//        this.categoriaServices = categoriaServices;
//    }
//    
    @GetMapping("/")
    public String inicio() {
        return "index";
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
