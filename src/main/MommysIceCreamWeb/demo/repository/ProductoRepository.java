package MommysIceCreamWeb.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import MommysIceCreamWeb.demo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
