package com.usco.starfilms_pw.repository;

import com.usco.starfilms_pw.model.Cartelera;
import com.usco.starfilms_pw.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CarteleraRepository extends JpaRepository<Cartelera, Long> {
    boolean existsByPelicula(Pelicula pelicula);

    List<Cartelera> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            LocalDate fechaActual, LocalDate fechaActual2
    );

    List<Cartelera> findByFechaInicioGreaterThan(LocalDate fechaActual);
}
