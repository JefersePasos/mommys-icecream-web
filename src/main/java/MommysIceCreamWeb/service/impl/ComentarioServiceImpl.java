package MommysIceCreamWeb.service.impl;

import MommysIceCreamWeb.dao.ComentarioRepository;
import MommysIceCreamWeb.domain.Comentario;
import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public void guardar(Comentario comentario) {
        comentarioRepository.save(comentario);
    }

    @Override
    public List<Comentario> listarAprobados(Producto producto) {
        return comentarioRepository.findByProductoAndAprobadoTrue(producto);
    }

    @Override
    public List<Comentario> listarTodos() {
        return comentarioRepository.findAll();
    }

    @Override
    public void aprobar(Long id) {
        comentarioRepository.findById(id).ifPresent(c -> {
            c.setAprobado(true);
            comentarioRepository.save(c);
        });
    }

    @Override
    public void eliminar(Long id) {
        comentarioRepository.deleteById(id);
    }
}
