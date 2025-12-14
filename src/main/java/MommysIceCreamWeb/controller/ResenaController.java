package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Comentario;
import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.repository.ComentarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/resenas")
public class ResenaController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @GetMapping
    public String mostrarResenas(Model model) {
        model.addAttribute(
            "comentarios",
            comentarioRepository.findAllByStatusTrueOrderByCreatedAtDesc()
        );
        model.addAttribute("comentario", new Comentario());
        return "resena/resena";
    }

    @PostMapping("/guardar")
    public String guardarResena(@ModelAttribute Comentario comentario, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        comentario.setUsuario(usuario);
        comentario.setStatus(true);
        comentario.setCreatedAt(LocalDateTime.now());
        comentario.setModifiedAt(LocalDateTime.now());

        comentarioRepository.save(comentario);

        return "redirect:/resenas";
    }
}