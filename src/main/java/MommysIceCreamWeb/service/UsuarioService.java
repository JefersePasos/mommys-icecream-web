package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.repository.UsuarioRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // para usar la encritpacion por nosotros
    //private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public void registrarUsuario(Usuario usuario) { //Método para registrar un nuevo usuario
        // aca hacemos el hash de la contrasena para guardarla de forma segura
        String hash = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(hash);
        // aca guardamos el usuario en la base de datos
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) { //Método para buscar un usuario por su correo electrónico e identificar si ya existe
        return usuarioRepository.findByCorreo(correo);
    }

    //---> Métodos para verificar y encriptar contraseñas sin usar el PasswordEncoder de Spring Security, eliminar después de pruebas <---//
    public boolean verificarPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    public String encriptarPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    //---> Fin de métodos para verificar y encriptar contraseñas sin usar el PasswordEncoder de Spring Security <---//

    public void eliminarUsuario(Long id) { //Método para eliminar un usuario por su ID
        usuarioRepository.deleteById(id);
    }
}