package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.domain.Rol;
import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.service.RolService;
import MommysIceCreamWeb.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/roles")
public class AdminRolController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public AdminRolController(UsuarioService usuarioService, RolService rolService) { // Inyección de dependencias
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    // Todo los metodos de mostrar formularios
    @GetMapping("/dashboard") // Mostrar el dashboard de gestión de roles | Apunta al dashboard.html en templates/admin/roles.
    public String dashboardRoles(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null || !usuario.getRol().getNombre().equalsIgnoreCase("Administrador")) { // Verificar si el usuario es administrador  
            return "redirect:/login";
        }

        List<Usuario> usuarios = usuarioService.listarTodos();
        List<Rol> roles = rolService.listarTodos();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);
        return "admin/roles/dashboard";
    }

    @GetMapping("/asignar/{id}") // Mostrar el formulario para asignar un rol a un usuario | Apunta al asignar_rol.html en templates/admin/roles.
    public String mostrarFormularioAsignarRol(@PathVariable Long id, HttpSession session, Model model) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorId(id).orElse(null); 
        if (usuario == null) {
            return "redirect:/admin/roles/dashboard";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listarTodos());
        return "admin/roles/asignar_rol";
    }

    @GetMapping("/editar/{id}") // Mostrar el formulario para editar un rol existente | Apunta al editar_rol.html en templates/admin/roles.
    public String mostrarFormularioEditarRol(@PathVariable Long id, HttpSession session, Model model) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Rol rol = rolService.buscarPorId(id).orElse(null);
        if (rol == null) {
            return "redirect:/admin/roles/dashboard";
        }

        model.addAttribute("rol", rol);
        return "admin/roles/editar_rol";
    }

    @GetMapping("/confirmar-revocacion/{id}") // Mostrar la confirmación para revocar el rol de un usuario | Apunta al confirmar_revocacion.html en templates/admin/roles.
    public String confirmarRevocacion(@PathVariable Long id, HttpSession session, Model model) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);
        if (usuario == null) {
            return "redirect:/admin/roles/dashboard";
        }

        model.addAttribute("usuario", usuario);
        return "admin/roles/confirmar_revocacion";
    }

    @GetMapping("/crear") // Mostrar el formulario para crear un nuevo rol | Apunta al crear_rol.html en templates/admin/roles.
    public String mostrarFormularioCrearRol(HttpSession session, Model model) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        model.addAttribute("rol", new Rol());
        return "admin/roles/crear_rol";
    }

    @GetMapping("/inactivar/{id}")// Mostrar la confirmación para inactivar un rol | Apunta al inactivar_rol.html en templates/admin/roles.
    public String mostrarConfirmacionInactivarRol(@PathVariable Long id, HttpSession session, Model model) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Rol rol = rolService.buscarPorId(id).orElse(null);
        if (rol == null || rol.getNombre().equalsIgnoreCase("Administrador") || rol.getNombre().equalsIgnoreCase("Cliente")) {
            return "redirect:/admin/roles/dashboard";
        }

        model.addAttribute("rol", rol);
        return "admin/roles/inactivar_rol";
    }


    // Todos los metodos de procesar formularios 
    @PostMapping("/asignar") // Procesar el formulario para asignar un rol a un usuario | Recibe los datos del formulario de asignar rol
    public String asignarRol(@RequestParam Long usuarioId, @RequestParam Long rolId, HttpSession session) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorId(usuarioId).orElse(null);
        Rol nuevoRol = rolService.buscarPorId(rolId).orElse(null);

        if (usuario != null && nuevoRol != null) {
            usuario.setRol(nuevoRol);
            usuarioService.registrarUsuario(usuario); // usar save() del servicio
        }

        return "redirect:/admin/roles/dashboard";
    }

    @PostMapping("/editar") // Procesar el formulario para editar un rol existente | Recibe los datos del formulario de editar rol
    public String editarRol(@ModelAttribute Rol rolEditado, HttpSession session) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Rol rolExistente = rolService.buscarPorId(rolEditado.getId()).orElse(null);
        if (rolExistente != null) {
            rolExistente.setNombre(rolEditado.getNombre());
            rolService.guardar(rolExistente);
        }

        return "redirect:/admin/roles/dashboard";
    }

    @PostMapping("/revocar/{id}") // Procesar la revocación de un rol a un usuario | Recibe el ID del usuario
    public String revocarRol(@PathVariable Long id, HttpSession session) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);
        Rol clienteRol = rolService.buscarPorNombre("Cliente").orElse(null);

        if (usuario != null && clienteRol != null) {
            usuario.setRol(clienteRol);
            usuarioService.registrarUsuario(usuario);
        }

        return "redirect:/admin/roles/dashboard";
    }

    @PostMapping("/crear") // Procesar el formulario para crear un nuevo rol | Recibe los datos del formulario de crear rol
    public String crearRol(@ModelAttribute Rol rol, HttpSession session, Model model) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        // Validar que no exista un rol con el mismo nombre
        if (rolService.buscarPorNombre(rol.getNombre()).isPresent()) {
            model.addAttribute("rol", rol);
            model.addAttribute("error", "Ya existe un rol con ese nombre.");
            return "admin/roles/crear_rol";
        }

        rolService.guardar(rol);
        return "redirect:/admin/roles/dashboard";
    }

    @PostMapping("/eliminar/{id}") // Procesar la eliminación de un rol | Recibe el ID del rol
    public String eliminarRol(@PathVariable Long id, HttpSession session, Model model) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Rol rol = rolService.buscarPorId(id).orElse(null);
        if (rol == null || rol.getNombre().equalsIgnoreCase("Administrador") || rol.getNombre().equalsIgnoreCase("Cliente")) {
            model.addAttribute("errorRol", "Este rol no puede ser eliminado.");
            return "redirect:/admin/roles/dashboard";
        }

        List<Usuario> usuariosConRol = usuarioService.listarTodos().stream()
            .filter(u -> u.getRol().getId().equals(id))
            .toList();

        if (!usuariosConRol.isEmpty()) {
            session.setAttribute("errorRol", "Este rol está en uso y no puede eliminarse.");
            return "redirect:/admin/roles/dashboard";
        }

        rolService.eliminar(id);
        return "redirect:/admin/roles/dashboard";
    }

    @PostMapping("/inactivar/{id}")
    public String inactivarRol(@PathVariable Long id, HttpSession session) {
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || !admin.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        Rol rol = rolService.buscarPorId(id).orElse(null);
        if (rol != null && !rol.getNombre().equalsIgnoreCase("Administrador") && !rol.getNombre().equalsIgnoreCase("Cliente")) {
            rol.setActivo(false);
            rolService.guardar(rol);
        }

        return "redirect:/admin/roles/dashboard";
    }
}