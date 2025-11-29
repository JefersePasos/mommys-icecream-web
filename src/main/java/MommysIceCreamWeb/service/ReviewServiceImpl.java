package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.domain.Review;
import MommysIceCreamWeb.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> obtenerReviewsAprobadas(Producto producto) {
        return reviewRepository.findByProductoAndApprovedTrue(producto);
    }

    @Override
    public void guardarReview(Review review) {
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);
    }

    @Override
    public List<Review> obtenerPendientes() {
        return reviewRepository.findByApprovedFalse();
    }

    @Override
    public void aprobar(Long id) {
        var opt = reviewRepository.findById(id);
        if (opt.isPresent()) {
            var review = opt.get();
            review.setApproved(true);
            reviewRepository.save(review);
        }
    }

    @Override
    public void eliminar(Long id) {
        reviewRepository.deleteById(id);
    }
}
