package MommysIceCreamWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/") // Mapea la raíz del sitio web
    public String mostrarIndex() {
        return "index"; // Retorna la vista "index.html" 
    }

    @GetMapping("/carrito") // Mapea la raíz del sitio web
    public String mostrarCarrito() {
        return "carrito"; // Retorna la vista "index.html" 
    }  

    @GetMapping("/comprar") // Mapea la raíz del sitio web
    public String mostrarCompra() {
        return "comprar"; // Retorna la vista "index.html" 
    }  

    @GetMapping("/contactenos") // Mapea la raíz del sitio web
    public String mostrarContacto() {
        return "contactenos"; // Retorna la vista "index.html" 
    }  

    @GetMapping("/sobre_nosotros") // Mapea la raíz del sitio web
    public String mostrarNosotros() {
        return "sobre_nosotros"; // Retorna la vista "index.html" 
    }  

    @GetMapping("/productos") // Mapea la raíz del sitio web
    public String mostrarProductos() {
        return "productos"; // Retorna la vista "index.html" 
    }

}
