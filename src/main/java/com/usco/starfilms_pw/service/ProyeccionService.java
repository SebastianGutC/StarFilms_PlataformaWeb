package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Proyeccion;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProyeccionService {
    Proyeccion saveProyeccion(Proyeccion proyeccion) throws Exception;

    Map<String, Map<LocalDate, List<Proyeccion>>> obtenerProyeccionesAgrupadas();

    public void deleteProyeccion(Long id);
}
