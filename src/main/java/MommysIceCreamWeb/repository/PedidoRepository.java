package MommysIceCreamWeb.repository;

import MommysIceCreamWeb.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}