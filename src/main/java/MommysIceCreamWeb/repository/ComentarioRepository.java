package MommysIceCreamWeb.repository;

import MommysIceCreamWeb.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findAllByStatusTrueOrderByCreatedAtDesc();

}