package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.Comentario;
import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.repository.ComentarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private HttpSession session;

    public void guardarComentario(String texto) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        Comentario comentario = new Comentario();
        comentario.setContenido(texto);
        comentario.setUsuario(usuario);
        comentario.setStatus(true);

        comentarioRepository.save(comentario);
    }

    public List<Comentario> obtenerComentariosActivos() {
        return comentarioRepository.findAllByStatusTrueOrderByCreatedAtDesc();
    }
}