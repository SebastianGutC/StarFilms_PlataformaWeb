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

@Controller
public class ProyeccionController {

    @Autowired
    private ProyeccionService proyeccionService;

    @Autowired
    private PeliculaServiceImpl peliculaServiceImpl;

    @Autowired
    private SalaService salaService;

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