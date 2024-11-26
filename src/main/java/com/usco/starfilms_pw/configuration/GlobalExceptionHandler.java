package com.usco.starfilms_pw.configuration;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Controlador global para el manejo de excepciones en la aplicación.
 * <p>
 * Esta clase está anotada con {@link ControllerAdvice} y maneja las excepciones que ocurren
 * durante la ejecución de la aplicación, proporcionando respuestas adecuadas a los usuarios en
 * caso de errores. Los métodos aquí definidos manejan excepciones específicas como {@link NoHandlerFoundException}
 * y {@link Exception}.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de tipo {@link NoHandlerFoundException}.
     * Esta excepción se lanza cuando el sistema no encuentra un controlador correspondiente a la URL solicitada.
     * El método añade un mensaje de error al modelo y redirige a la página de error.
     * @param model El objeto {@link Model} que se utiliza para agregar atributos a la vista.
     * @return El nombre de la vista de error.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(Model model) {
        model.addAttribute("error", "La página solicitada no fue encontrada.");
        return "error";
    }

    /**
     * Maneja las excepciones generales de tipo {@link Exception}.
     * Este método captura cualquier excepción no específica que ocurra durante la ejecución.
     * Se añade un mensaje de error con la descripción de la excepción al modelo y redirige a la página de error.
     * @param model El objeto {@link Model} que se utiliza para agregar atributos a la vista.
     * @param ex La excepción que ha sido lanzada durante la ejecución.
     * @return El nombre de la vista de error.
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception ex) {
        model.addAttribute("error", "Ha ocurrido un error inesperado: " + ex.getMessage());
        return "error";
    }
}