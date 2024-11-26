package com.usco.starfilms_pw.repository;

import com.usco.starfilms_pw.model.Proyeccion;
import com.usco.starfilms_pw.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long> {

    @Query("SELECT p FROM Proyeccion p WHERE p.sala = :sala " +
            "AND p.fechaProyeccion = :fecha " +
            "AND ((p.horaInicio <= :horaFin AND p.horaFin >= :horaInicio) " +
            "OR (p.horaInicio <= :horaInicio AND p.horaFin >= :horaInicio) " +
            "OR (p.horaInicio <= :horaFin AND p.horaFin >= :horaFin))")
    List<Proyeccion> findOverlappingProyecciones(Sala sala, LocalDate fecha,
                                                 LocalTime horaInicio, LocalTime horaFin);

    List<Proyeccion> findByFechaProyeccion(LocalDate fecha);
}