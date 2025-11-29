package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.Comentario;
import MommysIceCreamWeb.domain.Producto;

import java.util.List;

public interface ComentarioService {

    void guardar(Comentario comentario);

    List<Comentario> listarAprobados(Producto producto);

    List<Comentario> listarTodos(); // para ADMIN

    void aprobar(Long id);

    void eliminar(Long id);
}
