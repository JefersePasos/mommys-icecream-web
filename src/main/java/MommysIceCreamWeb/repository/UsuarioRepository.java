package MommysIceCreamWeb.repository;

import MommysIceCreamWeb.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { //JPARepository hereda métodos para CRUD (crear, leer, actualizar, eliminar)
    Optional<Usuario> findByCorreo(String correo);
}