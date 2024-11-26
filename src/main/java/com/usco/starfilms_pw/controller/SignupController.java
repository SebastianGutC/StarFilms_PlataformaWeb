package com.usco.starfilms_pw.controller;

import com.usco.starfilms_pw.dto.UsuarioRegistroDTO;
import com.usco.starfilms_pw.service.UsuarioExistenteException;
import com.usco.starfilms_pw.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Period;

/**
 * Controlador para gestionar el registro de nuevos usuarios.
 * Permite mostrar el formulario de registro y procesar la creación de un usuario.
 */
@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UsuarioService usuarioService;

    /**
     * Constructor que inyecta el servicio de usuario.
     *
     * @param usuarioService Servicio para gestionar usuarios.
     */
    public SignupController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Añade un objeto {@link UsuarioRegistroDTO} al modelo para ser usado en el formulario de registro.
     *
     * @return Un nuevo objeto UsuarioRegistroDTO.
     */
    @ModelAttribute("nuevoUsuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
        return new UsuarioRegistroDTO();
    }

    /**
     * Muestra el formulario de registro para los usuarios.
     *
     * @return Nombre de la vista del formulario de registro.
     */
    @GetMapping
    public String mostrarFormularioDeRegistro() {
        return "signup";
    }

    /**
     * Procesa el registro de un nuevo usuario.
     * Valida los datos ingresados, calcula la edad a partir de la fecha de nacimiento, y maneja posibles excepciones.
     *
     * @param usuarioRegistroDTO Objeto DTO que contiene los datos del usuario a registrar.
     * @param result             Objeto que contiene los resultados de la validación del formulario.
     * @param model              Modelo para pasar atributos a la vista.
     * @return el Nombre de la vista especifica para el formulario de registro.
     */
    @PostMapping
    public String registrarNuevoUsuario(@Valid @ModelAttribute("nuevoUsuario") UsuarioRegistroDTO usuarioRegistroDTO,
                                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("Errores de validación: " + result.getAllErrors());
            model.addAttribute("nuevoUsuario", usuarioRegistroDTO);
            model.addAttribute("errorMessage", "Error al registrar el usuario");
            return "signup";
        }

        if (usuarioRegistroDTO.getFechaNacimiento() != null) {
            int edad = calcularEdad(usuarioRegistroDTO.getFechaNacimiento());
            usuarioRegistroDTO.setEdad(edad);  // Establece la edad calculada
        }

        // depuración de la información del usuario
        System.out.println("Datos del usuario: " + usuarioRegistroDTO);

        try {
            usuarioService.guardar(usuarioRegistroDTO);
            model.addAttribute("successMessage", "El usuario se creó correctamente");
        } catch (UsuarioExistenteException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("usuario", usuarioRegistroDTO);
        } catch (Exception e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            model.addAttribute("errorMessage", "Error al registrar el usuario");
        }
        return "signup";
    }

    /**
     * Calcula la edad en años en función de la fecha de nacimiento proporcionada.
     *
     * @param fechaNacimiento Fecha de nacimiento del usuario.
     * @return Edad calculada en años.
     */
    private int calcularEdad(LocalDate fechaNacimiento) {
        LocalDate hoy = LocalDate.now();
        return Period.between(fechaNacimiento, hoy).getYears();
    }
}
