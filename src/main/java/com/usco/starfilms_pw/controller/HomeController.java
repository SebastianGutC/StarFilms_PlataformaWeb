package com.usco.starfilms_pw.controller;

import com.usco.starfilms_pw.model.Genero;
import com.usco.starfilms_pw.model.Pelicula;
import com.usco.starfilms_pw.model.Proyeccion;
import com.usco.starfilms_pw.model.Usuario;
import com.usco.starfilms_pw.repository.UsuarioRepository;
import com.usco.starfilms_pw.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controlador principal de la aplicación que maneja las páginas de inicio, inicio de sesión, página de usuario,
 * detalles de película, boletería y membresías.
 * Dependiendo de los roles del usuario (admin o usuario común), se muestran vistas diferentes.
 */
@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private PeliculaService peliculaService;
    private CarteleraService carteleraService;
    private ProyeccionService proyeccionService;

    /**
     * Constructor del controlador, inyectando las dependencias necesarias para manejar las solicitudes.
     *
     * @param usuarioService Servicio que maneja la lógica de usuarios.
     * @param usuarioRepository Repositorio que interactúa con la base de datos de usuarios.
     * @param peliculaService Servicio que maneja la lógica de las películas.
     * @param carteleraService Servicio que maneja la lógica de la cartelera.
     * @param proyeccionService Servicio que maneja la lógica de las proyecciones.
     */
    public HomeController(UsuarioService usuarioService, UsuarioRepository usuarioRepository,
                          PeliculaService peliculaService, CarteleraService carteleraService,
                          ProyeccionService proyeccionService) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.peliculaService = peliculaService;
        this.carteleraService = carteleraService;
        this.proyeccionService = proyeccionService;
    }

    /**
     * Método que maneja la página de inicio de la aplicación. Muestra las cartelera actual y las próximas películas.
     *
     * @param model El modelo que contiene los atributos para la vista.
     * @return El nombre de la vista de inicio (index).
     */
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("carteleraActual", carteleraService.getCarteleraActual());
        model.addAttribute("carteleraProximamente", carteleraService.getCarteleraProximamente());
        return "index";
    }

    /**
     * Método que maneja la página de inicio de sesión.
     *
     * @return El nombre de la vista de inicio de sesión (login).
     */
    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    /**
     * Método que maneja la página principal de la aplicación. Si el usuario es un administrador, se muestra la vista
     * de administración con detalles de películas y cartelera; si es un usuario común, se muestra la vista de usuario.
     *
     * @param authentication El objeto que contiene la información de autenticación del usuario.
     * @param model El modelo que contiene los atributos para la vista.
     * @param principal El objeto que contiene el nombre del usuario autenticado.
     * @param fecha Fecha opcional para filtrar las proyecciones.
     * @return El nombre de la vista correspondiente según el rol del usuario (adminhome o userhome).
     */
    @GetMapping("/home")
    public String homePage(Authentication authentication, Model model, Principal principal,
                           @RequestParam(required = false) String fecha) {
        String correo = principal.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
        model.addAttribute("usuario", usuario);
        model.addAttribute("nombreCompleto", nombreCompleto);
        Map<String, Map<LocalDate, List<Proyeccion>>> proyeccionesAgrupadas = proyeccionService.obtenerProyeccionesAgrupadas();
        model.addAttribute("proyeccionesAgrupadas", proyeccionesAgrupadas);

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            List<Pelicula> peliculas = peliculaService.getAllPeliculas();
            model.addAttribute("peliculas", peliculas);
            model.addAttribute("carteleraActual", carteleraService.getCarteleraActual());
            model.addAttribute("carteleraProximamente", carteleraService.getCarteleraProximamente());
            return "adminhome"; // Vista para administradores
        } else {
            return "userhome"; // Vista para usuarios
        }
    }

    /**
     * Método que muestra los detalles de una película seleccionada.
     *
     * @param id El ID de la película cuya información se desea mostrar.
     * @param model El modelo que contiene los atributos para la vista.
     * @return El nombre de la vista de detalles de película (peliculadetail).
     */
    @GetMapping("/pelicula/{id}")
    public String showPeliculaDetail(@PathVariable(value = "id") Long id, Model model) {
        if (!model.containsAttribute("pelicula")) {
            Pelicula pelicula = peliculaService.listById(id);
            model.addAttribute("pelicula", pelicula);
        }
        return "peliculadetail";
    }

    /**
     * Método que maneja la página de boletería de la aplicación.
     *
     * @return El nombre de la vista de boletería (boleteria).
     */
    @GetMapping("/boleteria")
    public String showBoleteria() {
        return "boleteria";
    }

    /**
     * Método que maneja la página de membresías de la aplicación.
     *
     * @return El nombre de la vista de membresías (membresias).
     */
    @GetMapping("/membresias")
    public String showMembresias() {
        return "membresias";
    }
}