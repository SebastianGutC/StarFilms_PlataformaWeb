package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Proyeccion;
import com.usco.starfilms_pw.repository.ProyeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar las proyecciones de películas.
 * <p>
 * Esta clase implementa la interfaz {@link ProyeccionService} y proporciona la lógica de negocio para interactuar con
 * el repositorio {@link ProyeccionRepository}. Permite realizar operaciones como guardar, obtener y eliminar proyecciones,
 * así como validar que las proyecciones no se solapen y organizar las proyecciones por película y fecha.
 */
@Service
public class ProyeccionServiceImpl implements ProyeccionService {

    @Autowired
    private ProyeccionRepository proyeccionRepository;

    /**
     * Guarda una nueva proyección en la base de datos.
     * <p>
     * Este método realiza las siguientes validaciones antes de guardar la proyección:
     * <ul>
     *     <li>Verifica que la hora de finalización sea posterior a la hora de inicio.</li>
     *     <li>Verifica si la proyección se solapa con alguna otra proyección en la misma sala y fecha.</li>
     * </ul>
     * Si las validaciones son correctas, se guarda la proyección en la base de datos.
     * @param proyeccion la proyección que se desea guardar.
     * @return la proyección guardada.
     * @throws Exception si la hora de finalización es antes de la hora de inicio o si existen proyecciones superpuestas.
     */
    @Override
    public Proyeccion saveProyeccion(Proyeccion proyeccion) throws Exception {
        // Validar que la hora de fin sea después de la hora de inicio
        if (proyeccion.getHoraFin().isBefore(proyeccion.getHoraInicio())) {
            throw new Exception("La hora de finalización debe ser posterior a la hora de inicio");
        }

        // Buscar proyecciones que se solapan
        List<Proyeccion> proyeccionesSuperpuestas = proyeccionRepository.findOverlappingProyecciones(
                proyeccion.getSala(),
                proyeccion.getFechaProyeccion(),
                proyeccion.getHoraInicio(),
                proyeccion.getHoraFin()
        );

        // Si hay proyecciones superpuestas, lanzar excepción
        if (!proyeccionesSuperpuestas.isEmpty()) {
            throw new Exception("La sala ya está ocupada en el horario seleccionado");
        }

        // Si no hay superposición, guardar la proyección
        return proyeccionRepository.save(proyeccion);
    }

    /**
     * Obtiene todas las proyecciones agrupadas por película y fecha.
     * Este método obtiene todas las proyecciones y las agrupa primero por el nombre de la película y luego por la fecha de la proyección.
     * @return un mapa que contiene el nombre de la película como clave y otro mapa como valor, donde la clave es la fecha de la proyección
     * y el valor es una lista de proyecciones para esa fecha.
     */
    @Override
    public Map<String, Map<LocalDate, List<Proyeccion>>> obtenerProyeccionesAgrupadas() {
        List<Proyeccion> proyecciones = proyeccionRepository.findAll();

        // Agrupar por película y luego por fecha
        return proyecciones.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getPelicula().getNombre(), // Agrupar por título de película
                        Collectors.groupingBy(Proyeccion::getFechaProyeccion) // Sub-agrupación por fecha
                ));
    }

    /**
     * Elimina una proyección por su ID.
     * Este método elimina la proyección correspondiente al ID proporcionado. Si no se encuentra la proyección con ese ID,
     * se lanza una excepción {@link RuntimeException}.
     * @param id el ID de la proyección que se desea eliminar.
     * @throws RuntimeException si no se encuentra la proyección o si ocurre un error al eliminarla.
     */
    @Override
    public void deleteProyeccion(Long id) {
        try {
            if (proyeccionRepository.existsById(id)) {
                proyeccionRepository.deleteById(id);
            } else {
                throw new RuntimeException("Horario de Proyección no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la Proyección: " + e.getMessage());
        }
    }
}