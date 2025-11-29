package MommysIceCreamWeb.repository;

import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Obtener solo reseñas aprobadas por producto
    List<Review> findByProductoAndApprovedTrue(Producto producto);

    // Obtener reseñas pendientes (para moderación)
    List<Review> findByApprovedFalse();
}
