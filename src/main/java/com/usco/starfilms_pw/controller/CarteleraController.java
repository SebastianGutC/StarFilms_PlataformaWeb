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


@Controller
public class CarteleraController {

    @Autowired
    CarteleraService carteleraService;

    @Autowired
    private PeliculaServiceImpl peliculaServiceImpl;

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
