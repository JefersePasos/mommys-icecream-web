package MommysIceCreamWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import MommysIceCreamWeb.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}