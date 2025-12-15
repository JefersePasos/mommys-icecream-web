package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.Rol;
import MommysIceCreamWeb.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Rol> listarTodos() {
        return rolRepository.findAll(Sort.by("nombre").ascending());
    }

    public Optional<Rol> buscarPorNombre(String nombre) {
        return rolRepository.findByNombreIgnoreCase(nombre);
    }

    public String traducirInglesAEspanol(String nombreEn) {
        if (nombreEn == null) return null;
        String clave = nombreEn.trim().toLowerCase();
        return switch (clave) {
            case "administrator", "admin" -> "Administrador";
            case "customer", "client", "usuario" -> "Cliente";
            case "moderator" -> "Moderador";
            case "seller" -> "Vendedor";
            case "test" -> "Prueba";
            default -> nombreEn;
        };
    }

    public Rol guardar(Rol rol) {
        // Si no viene el nombre en inglés, o no coincide con la traducción actual, lo generamos automáticamente.
        if (StringUtils.hasText(rol.getNombre())) {
            String autoEn = traducirEspanolAIngles(rol.getNombre());
            if (!StringUtils.hasText(rol.getNombreEn()) || !rol.getNombreEn().equalsIgnoreCase(autoEn)) {
                rol.setNombreEn(autoEn);
            }
        } else if (StringUtils.hasText(rol.getNombreEn())) {
            // Si solo viene nombreEn, usamos ese valor como nombre por defecto.
            rol.setNombre(traducirInglesAEspanol(rol.getNombreEn()));
        }
        return rolRepository.save(rol);
    }

    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }

    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    /**
     * Traducción básica ES -> EN para roles conocidos.
     * Si no hay mapeo, replica el nombre original.
     */
    public String traducirEspanolAIngles(String nombreEs) {
        if (nombreEs == null) return null;
        String clave = nombreEs.trim().toLowerCase();
        return switch (clave) {
            case "administrador", "admin" -> "Administrator";
            case "cliente", "usuario" -> "Customer";
            case "moderador" -> "Moderator";
            case "vendedor" -> "Seller";
            case "prueba" -> "Test";
            case "pruebas" -> "Test";
            default -> nombreEs;
        };
    }
}
