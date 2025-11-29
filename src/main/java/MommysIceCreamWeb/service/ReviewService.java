package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.domain.Review;
import java.util.List;

public interface ReviewService {

    List<Review> obtenerReviewsAprobadas(Producto producto);

    void guardarReview(Review review);

    List<Review> obtenerPendientes();

    void aprobar(Long id);

    void eliminar(Long id);
}
