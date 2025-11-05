package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.Rol;
import MommysIceCreamWeb.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

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

    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }

    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }
}