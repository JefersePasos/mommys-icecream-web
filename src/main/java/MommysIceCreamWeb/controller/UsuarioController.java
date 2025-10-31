package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Telefono;
import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import java.security.Principal;
import java.util.List;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;
    //private final PasswordEncoder passwordEncoder; // Para verificar la contraseña
    

    //public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder){// Inyección de dependencias
    public UsuarioController(UsuarioService usuarioService){ 
        this.usuarioService = usuarioService;
        //this.passwordEncoder = passwordEncoder; // Inicializar el PasswordEncoder
    }

    //Todo los metodos de mostrar formularios 
    @GetMapping("/registro") // Mostrar el formulario de registro | Apunta al registro.html en templates.
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }
    @GetMapping("/login") // Mostrar el formulario de login | Apunta al login.html en templates.
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @GetMapping("/perfil")// Mostrar perfil del usuario | Apunta al perfil.html en templates.
    //public String mostrarPerfil(Model model, Principal principal) {
    public String mostrarPerfil(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        //if (principal == null) {
        if (usuario == null) {
            return "redirect:/login";
        }

        //String correo = principal.getName();
        //Usuario usuario = usuarioService.buscarPorCorreo(correo).orElse(null);
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    //@GetMapping("/logout") // Cerrar sesión | No recibe datos
    //public String cerrarSesion(HttpSession session) {
    //    session.invalidate(); // Elimina la sesión
    //    return "redirect:/login"; // Redirigir al login 
    //}

    @GetMapping("/editar-perfil") // Mostrar el formulario de edición de perfil | Apunta al editar_perfil.html en templates.
    public String mostrarFormularioEdicion(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "editar_perfil";
    }

    @GetMapping("/cuenta-eliminada") // Mostrar página de cuenta eliminada | Apunta al cuenta_eliminada.html en templates.
    public String cuentaEliminada() {
        return "cuenta_eliminada";
    }

    @GetMapping("/confirmar-eliminacion") // Mostrar página de confirmación de eliminación de cuenta | Apunta al confirmar_eliminacion.html en templates.
    public String mostrarConfirmacionEliminacion(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        return "confirmar_eliminacion";
    }

    //Todo los metodos de procesar formularios
    @PostMapping("/registro") // Procesar el formulario de registro | Recibe los datos del formulario de registro
    public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario, @RequestParam(name = "telefonos[0].numero", required = false) String telefonoInput, Model model) {

        if (usuarioService.buscarPorCorreo(usuario.getCorreo()).isPresent()) { // Validar si ya existe el correo
            model.addAttribute("error", "Ya existe una cuenta con ese correo.");
            return "registro";
        }

        // Si se ingresó un número, crear objeto Telefono
        if (telefonoInput != null && !telefonoInput.isBlank()) {
            Telefono tel = new Telefono();
            tel.setNumero(telefonoInput);
            tel.setUsuario(usuario);
            usuario.setTelefonos(List.of(tel));
        }

        usuarioService.registrarUsuario(usuario); //Metodo para confirmar el registro
        model.addAttribute("mensaje", "Cuenta creada exitosamente. Ahora puedes iniciar sesión.");
        return "redirect:/login"; // Redirigir al login tras registro exitoso
    }

    @PostMapping("/login") // Procesar el formulario de login | Recibe los datos del formulario de login
    public String procesarLogin(@ModelAttribute("usuario") Usuario usuario, Model model, HttpSession session) {
        var usuarioOptional = usuarioService.buscarPorCorreo(usuario.getCorreo());

        if (usuarioOptional.isPresent()) {
            Usuario usuarioDb = usuarioOptional.get();

            //if (passwordEncoder.matches(usuario.getContrasena(), usuarioDb.getContrasena())){ // Verificar la contraseña y compararla con el hash almacenada
            //if (usuarioService.verificarPassword(usuario.getContrasena(), usuarioDb.getContrasena())) {
                 //Verificar la contraseña usando el servicio en texto plano
            if (usuarioDb.getContrasena().equals(usuario.getContrasena())) {
                session.setAttribute("usuarioLogueado", usuarioDb);// Guardar usuario en sesión
                return "redirect:/perfil"; // Página privada 
            }
        }

        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "login";
    }

    @PostMapping("/editar-perfil") // Procesar el formulario de edición de perfil | Recibe los datos del formulario de edición de perfil
    public String procesarEdicionPerfil(@ModelAttribute("usuario") Usuario datosActualizados, HttpSession session, Model model) {
        Usuario usuarioOriginal = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioOriginal == null) {
            return "redirect:/login";
        }

        // Aca actualizamo campos
        usuarioOriginal.setNombre(datosActualizados.getNombre());
        usuarioOriginal.setPrimerApellido(datosActualizados.getPrimerApellido());
        usuarioOriginal.setSegundoApellido(datosActualizados.getSegundoApellido());
        usuarioOriginal.setCorreo(datosActualizados.getCorreo());
        usuarioOriginal.setProvincia(datosActualizados.getProvincia());
        usuarioOriginal.setCanton(datosActualizados.getCanton());
        usuarioOriginal.setDistrito(datosActualizados.getDistrito());
        usuarioOriginal.setDireccionExacta(datosActualizados.getDireccionExacta());

        // Si se escribió una nueva contraseña, se actualiza el hash
        //if (datosActualizados.getContrasena() != null && !datosActualizados.getContrasena().isBlank()) {
            //String nuevaHash = passwordEncoder.encode(datosActualizados.getContrasena());
            //usuarioOriginal.setContrasena(nuevaHash);
        //}
        
        //Sin seguridad de encriptacion.
        if (datosActualizados.getContrasena() != null && !datosActualizados.getContrasena().isBlank()) {
            usuarioOriginal.setContrasena(usuarioService.encriptarPassword(datosActualizados.getContrasena()));
        }

        usuarioService.registrarUsuario(usuarioOriginal);
        session.setAttribute("usuarioLogueado", usuarioOriginal);
        model.addAttribute("mensaje", "Perfil actualizado correctamente");

        return "editar_perfil";
    }

    @PostMapping("/logout") // Cerrar sesión | No recibe datos
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
       return "redirect:/login";
    }

    @PostMapping("/eliminar-cuenta") // Procesar la eliminación de la cuenta del usuario | No recibe datos
    public String eliminarCuenta(@RequestParam("contrasena") String contrasena,HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        /*if (usuario != null) {
            usuarioService.eliminarUsuario(usuario.getId());
            session.invalidate();
        }*/
        if (usuario == null) {
            return "redirect:/login";
        }

        System.out.println("Contraseña ingresada: " + contrasena);
        System.out.println("Contraseña real: " + usuario.getContrasena());

        if (!usuario.getContrasena().equals(contrasena)) {// Validar contraseña (plana, sin BCrypt)
            model.addAttribute("error", "La contraseña no coincide.");
            return "confirmar_eliminacion"; // ← No elimina nada
        }

        usuarioService.eliminarUsuario(usuario.getId()); //Solo si coincide se elimina
        session.invalidate();
        return "cuenta_eliminada"; // o a una página de confirmación
    }
}