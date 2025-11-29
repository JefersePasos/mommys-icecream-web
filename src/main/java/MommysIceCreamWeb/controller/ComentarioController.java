package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Comentario;
import MommysIceCreamWeb.service.ComentarioService;
import MommysIceCreamWeb.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ProductoService productoService;

    @PostMapping("/guardar/{productoId}")
    public String guardar(@PathVariable Long productoId,
                          @ModelAttribute Comentario comentario) {

        var producto = productoService.obtenerPorId(productoId);

        if (producto.isPresent()) {
            comentario.setProducto(producto.get());
            comentarioService.guardar(comentario);
        }

        return "redirect:/productos/detalle/" + productoId;
    }
}
