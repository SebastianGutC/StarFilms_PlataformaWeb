package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Proyeccion;
import com.usco.starfilms_pw.repository.ProyeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProyeccionServiceImpl implements ProyeccionService {

    @Autowired
    private ProyeccionRepository proyeccionRepository;

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