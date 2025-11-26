package MommysIceCreamWeb.repository;

import MommysIceCreamWeb.domain.Pedido;
import MommysIceCreamWeb.domain.Usuario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// PedidoRepository.java
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioOrderByCreatedAtDesc(Usuario usuario);
    Page<Pedido> findByUsuario(Usuario usuario, Pageable pageable);
}