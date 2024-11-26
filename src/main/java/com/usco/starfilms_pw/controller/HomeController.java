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

@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private PeliculaService peliculaService;
    public CarteleraService carteleraService;
    private ProyeccionService proyeccionService;

    public HomeController(UsuarioService usuarioService, UsuarioRepository usuarioRepository, PeliculaService peliculaService, CarteleraService carteleraService, ProyeccionService proyeccionService) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.peliculaService = peliculaService;
        this.carteleraService = carteleraService;
        this.proyeccionService = proyeccionService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("carteleraActual", carteleraService.getCarteleraActual());
        model.addAttribute("carteleraProximamente", carteleraService.getCarteleraProximamente());
        return "index";
    }

    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Authentication authentication, Model model, Principal principal,@RequestParam(required = false) String fecha) {
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

    @GetMapping("/pelicula/{id}")
    public String showPeliculaDetail(@PathVariable(value = "id") Long id, Model model) {
        if (!model.containsAttribute("pelicula")) {
            Pelicula pelicula = peliculaService.listById(id);
            model.addAttribute("pelicula", pelicula);
        }
        return "peliculadetail";
    }


    @GetMapping("/boleteria")
    public String showBoleteria() {
        return "boleteria";
    }

    @GetMapping("/membresias")
    public String showMembresias() {
        return "membresias";
    }

}
