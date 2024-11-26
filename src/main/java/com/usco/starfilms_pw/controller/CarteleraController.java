package com.usco.starfilms_pw.controller;

import com.usco.starfilms_pw.model.Cartelera;
import com.usco.starfilms_pw.model.Pelicula;
import com.usco.starfilms_pw.service.CarteleraService;
import com.usco.starfilms_pw.service.PeliculaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con la cartelera de películas.
 * Permite mostrar un formulario para agregar películas a la cartelera, guardar cambios, y eliminar películas de la cartelera.
 */
@Controller
public class CarteleraController {

    @Autowired
    private CarteleraService carteleraService;

    @Autowired
    private PeliculaServiceImpl peliculaServiceImpl;

    /**
     * Muestra el formulario para agregar una película a la cartelera.
     *
     * @param id El ID de la película a agregar.
     * @param model El modelo para pasar los datos al frontend.
     * @param redirectAttributes Atributos de redirección para mensajes flash.
     * @return La vista del formulario para agregar la película a la cartelera.
     * @throws RuntimeException Si la película no se encuentra.
     */
    @GetMapping("/formcartelera/{id}")
    public String showFormCartelera(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        Cartelera cartelera = new Cartelera();
        Pelicula pelicula = peliculaServiceImpl.getPeliculaById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        cartelera.setPelicula(pelicula);
        model.addAttribute("cartelera", cartelera);

        return "addcartelera";
    }

    /**
     * Guarda una película en la cartelera.
     *
     * @param id El ID de la película.
     * @param cartelera Los datos de la cartelera a guardar.
     * @param result El resultado de las validaciones del formulario.
     * @param flash Atributos de redirección para mensajes flash.
     * @return Redirección a la vista del formulario con mensajes de éxito o error.
     */
    @PostMapping("/addcartelera/{id}")
    public String savePelicula(
            @PathVariable(value = "id") Long id,
            @Validated @ModelAttribute("cartelera") Cartelera cartelera,
            BindingResult result,
            RedirectAttributes flash) {

        if (result.hasErrors()) {
            return "addcartelera";
        }

        try {
            carteleraService.saveCartelera(cartelera);
            flash.addFlashAttribute("successMessage", "Película guardada exitosamente en cartelera.");
            return "redirect:/formcartelera/" + id;
        } catch (Exception e) {
            flash.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/formcartelera/" + id;
        }
    }

    /**
     * Elimina una película de la cartelera.
     *
     * @param id El ID de la película a eliminar de la cartelera.
     * @param flash Atributos de redirección para mensajes flash.
     * @return Redirección a la página de inicio con mensaje de éxito o error.
     */
    @GetMapping("/deletecartelera/{id}")
    public String deleteCartelera(@PathVariable Long id, RedirectAttributes flash) {
        System.out.println("Intentando eliminar la película con cartelera ID: " + id);
        try {
            carteleraService.deleteCartelera(id);
            flash.addFlashAttribute("successMessage", "Película eliminada con éxito.");
            return "redirect:/home";
        } catch (RuntimeException e) {
            flash.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/home";
        }
    }
}

