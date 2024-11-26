package com.usco.starfilms_pw.controller;

import com.usco.starfilms_pw.model.Pelicula;
import com.usco.starfilms_pw.model.Proyeccion;
import com.usco.starfilms_pw.model.Sala;
import com.usco.starfilms_pw.service.PeliculaServiceImpl;
import com.usco.starfilms_pw.service.ProyeccionService;
import com.usco.starfilms_pw.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador encargado de gestionar las proyecciones de películas en el sistema.
 * Permite la creación, eliminación y visualización de proyecciones en salas específicas.
 */
@Controller
public class ProyeccionController {

    @Autowired
    private ProyeccionService proyeccionService;

    @Autowired
    private PeliculaServiceImpl peliculaServiceImpl;

    @Autowired
    private SalaService salaService;

    /**
     * Muestra el formulario para agregar una nueva proyección para una película específica.
     *
     * @param id El ID de la película para la que se desea agregar una proyección.
     * @param model El objeto Model que se utiliza para pasar datos a la vista.
     * @param redirectAttributes Los atributos que se pasan para redirigir con mensajes flash.
     * @return La vista para agregar una proyección.
     */
    @GetMapping("/formproyeccion/{id}")
    public String showFormProyeccion(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        Proyeccion proyeccion = new Proyeccion();
        Pelicula pelicula = peliculaServiceImpl.getPeliculaById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        proyeccion.setPelicula(pelicula);

        // Obtener la lista de salas y agregarla al modelo
        List<Sala> salas = salaService.getAllSalas();
        model.addAttribute("salas", salas);
        model.addAttribute("proyeccion", proyeccion);

        return "addproyeccion";
    }

    /**
     * Guarda una nueva proyección en el sistema.
     *
     * @param id El ID de la película relacionada con la proyección.
     * @param proyeccion El objeto Proyeccion que contiene los datos a guardar.
     * @param result El resultado de las validaciones del formulario.
     * @param flash Los atributos de redirección para enviar mensajes flash.
     * @return Redirige a la vista de creación de proyección o muestra errores si los hay.
     */
    @PostMapping("/addproyeccion/{id}")
    public String saveProyeccion(
            @PathVariable(value = "id") Long id,
            @Validated @ModelAttribute("proyeccion") Proyeccion proyeccion,
            BindingResult result,
            RedirectAttributes flash) {

        if (result.hasErrors()) {
            return "addproyeccion";
        }

        try {
            proyeccionService.saveProyeccion(proyeccion);
            flash.addFlashAttribute("successMessage", "Proyección guardada exitosamente");
            return "redirect:/formproyeccion/" + id;
        } catch (Exception e) {
            flash.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/formproyeccion/" + id;
        }
    }

    /**
     * Elimina una proyección del sistema.
     *
     * @param id El ID de la proyección a eliminar.
     * @param flash Los atributos de redirección para enviar mensajes flash.
     * @return Redirige a la página principal con un mensaje de éxito o error.
     */
    @GetMapping("/deleteproyeccion/{id}")
    public String deleteProyeccion(@PathVariable Long id, RedirectAttributes flash) {
        System.out.println("Intentando eliminar el horario con Proyeccion ID: " + id);
        try {
            proyeccionService.deleteProyeccion(id);
            flash.addFlashAttribute("successMessage", "Horario de Proyección eliminado con éxito.");
            return "redirect:/home";
        } catch (RuntimeException e) {
            flash.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/home";
        }
    }
}