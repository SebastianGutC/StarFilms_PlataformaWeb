package com.usco.starfilms_pw.controller;

import java.net.MalformedURLException;
import java.util.List;


import com.usco.starfilms_pw.model.Genero;
import com.usco.starfilms_pw.model.Pelicula;
import com.usco.starfilms_pw.service.GeneroService;
import com.usco.starfilms_pw.service.IUploadFileService;
import com.usco.starfilms_pw.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    private GeneroService generoService;


    @GetMapping(value = "/uploads/{filename}")
    public ResponseEntity<Resource> goImage(@PathVariable String filename) {
        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/formpelicula")
    public String showNewformPelicula(Model model) {
        model.addAttribute("pelicula", new Pelicula());

        List<Genero> generos = generoService.findAll();
        model.addAttribute("generos", generos);
        return "addpelicula";
    }

    @PostMapping("/addpelicula")
    public String savePelicula(
            @Validated @ModelAttribute("pelicula") Pelicula pelicula,
            BindingResult result,
            Model model,
            @RequestParam("file") MultipartFile image,
            RedirectAttributes flash,
            SessionStatus status) throws Exception {

        if (result.hasErrors()) {
            System.out.println("Errores detectados en el formulario:");
            result.getAllErrors().forEach(error -> {
                System.out.println(" - " + error.getDefaultMessage());
            });

            model.addAttribute("generos", generoService.findAll());
            return "addpelicula";
        }

        // Validar género seleccionado
        if (pelicula.getGenero() == null || pelicula.getGenero().getGeneroId() == null) {
            result.rejectValue("genero", "error.genero", "Debe seleccionar un género válido.");
            model.addAttribute("generos", generoService.findAll());

            return "addpelicula";
        }

        Genero generoSeleccionado = generoService.findById(pelicula.getGenero().getGeneroId());
        pelicula.setGenero(generoSeleccionado);

        // Manejo del archivo (poster)
        if (!image.isEmpty()) {
            if (pelicula.getPeliculaId() != null && pelicula.getImage() != null) {
                uploadFileService.delete(pelicula.getImage());
            }
            String uniqueFileName = uploadFileService.copy(image);
            pelicula.setImage(uniqueFileName);
        }

        peliculaService.savePelicula(pelicula);
        status.setComplete();

        flash.addFlashAttribute("successMessage", "Película guardada exitosamente.");
        return "redirect:/formpelicula";
    }

    @PostMapping("/updatepelicula/{id}")
    public String updatePelicula(
            @PathVariable(value = "id") Long id,
            @Validated @ModelAttribute("pelicula") Pelicula pelicula,
            BindingResult result,
            Model model,
            RedirectAttributes flash,
            SessionStatus status) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    System.out.println("Campo: " + fieldError.getField());
                    System.out.println("Mensaje de error: " + fieldError.getDefaultMessage());
                    System.out.println("Valor rechazado: " + fieldError.getRejectedValue());
                } else {
                    System.out.println("Error global: " + error.getDefaultMessage());
                }
            });

            Pelicula peliculaExistente = peliculaService.listById(id);
            pelicula.setImage(peliculaExistente.getImage());

            model.addAttribute("generos", generoService.findAll());
            return "redirect:/admdetail/" + id;
        }

        try {
            // Obtener la película original
            Pelicula peliculaExistente = peliculaService.listById(id);

            // Mantener la imagen original
            pelicula.setImage(peliculaExistente.getImage());

            // Mantener la fecha de estreno original si no se ingresa una nueva
            if (pelicula.getFechaEstreno() == null) {
                pelicula.setFechaEstreno(peliculaExistente.getFechaEstreno());
            }

            // Validar y establecer el género
            if (pelicula.getGenero() != null && pelicula.getGenero().getGeneroId() != null) {
                Genero generoSeleccionado = generoService.findById(pelicula.getGenero().getGeneroId());
                pelicula.setGenero(generoSeleccionado);
            } else {
                result.rejectValue("genero", "error.genero", "Debe seleccionar un género válido.");
                model.addAttribute("generos", generoService.findAll());
                return "admindetailpelicula";
            }

            pelicula.setPeliculaId(id);

            peliculaService.savePelicula(pelicula);
            status.setComplete();

            flash.addFlashAttribute("successMessage", "Película actualizada exitosamente.");
            return "redirect:/admdetail/" + id;
        } catch (Exception e) {
            flash.addFlashAttribute("errorMessage", "Error al actualizar la película: " + e.getMessage());
            return "redirect:/admdetail/" + id;
        }
    }


    @GetMapping("/admdetail/{id}")
    public String goDetailPelicula(@PathVariable(value = "id") Long id, Model model) {
        // Si el modelo no contiene la película, añádela
        if (!model.containsAttribute("pelicula")) {
            Pelicula pelicula = peliculaService.listById(id);
            model.addAttribute("pelicula", pelicula);
        }
        // Añade los géneros
        List<Genero> generos = generoService.findAll();
        model.addAttribute("generos", generos);

        return "admindetailpelicula";
    }


}
