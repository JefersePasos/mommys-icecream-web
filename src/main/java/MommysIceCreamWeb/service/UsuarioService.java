package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.Rol;
import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.repository.RolRepository;
import MommysIceCreamWeb.repository.UsuarioRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    //private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // para usar la encritpacion por nosotros
    //private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) { 
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public void registrarUsuario(Usuario usuario) { //Método para registrar un nuevo usuario
////         aca hacemos el hash de la contrasena para guardarla de forma segura
//        String hash = passwordEncoder.encode(usuario.getContrasena());
//        usuario.setContrasena(hash);
//         aca guardamos el usuario en la base de datos

        if (usuario.getRol() == null) { // Asignar rol "Cliente" por defecto si no se ha asignado ningún rol
            Rol clienteRol = rolRepository.findByNombreIgnoreCase("Cliente")
                    .orElseThrow(() -> new RuntimeException("Rol 'Cliente' no existe"));
            usuario.setRol(clienteRol);
        }
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) { //Método para buscar un usuario por su correo electrónico e identificar si ya existe
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<Usuario> buscarPorId(Long id) { //Método para buscar un usuario por su ID
        return usuarioRepository.findById(id);
    }

    public List<Usuario> listarTodos() { //Método para listar todos los usuarios
        return usuarioRepository.findAll();
    }

    //---> Métodos para verificar y encriptar contraseñas sin usar el PasswordEncoder de Spring Security, eliminar después de pruebas <---//
    /*public boolean verificarPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }*/

    public String encriptarPassword(String Password) {
        return Password;
    }
    //---> Fin de métodos para verificar y encriptar contraseñas sin usar el PasswordEncoder de Spring Security <---//

    public void eliminarUsuario(Long id) { //Método para eliminar un usuario por su ID
        usuarioRepository.deleteById(id);
    }
}