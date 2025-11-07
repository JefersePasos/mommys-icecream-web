package MommysIceCreamWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import MommysIceCreamWeb.domain.Producto;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByStatusTrue();
}
