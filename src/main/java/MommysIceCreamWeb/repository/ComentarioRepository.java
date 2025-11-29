package MommysIceCreamWeb.dao;

import MommysIceCreamWeb.domain.Comentario;
import MommysIceCreamWeb.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByProductoAndAprobadoTrue(Producto producto);
}
