package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa una cartelera de proyección en el sistema.
 * Esta clase almacena la información relacionada con las fechas de inicio y fin de una cartelera
 * que presenta una película específica. Cada cartelera está asociada a una película y tiene un período
 * de proyección determinado por las fechas de inicio y fin.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cartelera")
public class Cartelera {

    /**
     * Identificador único de la cartelera.
     * Este campo se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carteleraId;

    /**
     * Fecha de inicio de la cartelera.
     * Representa el primer día en que una película comienza a proyectarse en la cartelera.
     * Este campo no puede ser nulo.
     */
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    /**
     * Fecha de fin de la cartelera.
     * Representa el último día en que una película está disponible en la cartelera.
     * Este campo no puede ser nulo.
     */
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    /**
     * Película asociada a la cartelera.
     * Cada cartelera está asociada a una película específica.
     * Este campo no puede ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    /**
     * Constructor con parámetros para inicializar los atributos de la cartelera.
     *
     * @param fechaInicio La fecha de inicio de la cartelera.
     * @param fechaFin La fecha de fin de la cartelera.
     * @param pelicula La película asociada a la cartelera.
     */
    public Cartelera(LocalDate fechaInicio, LocalDate fechaFin, Pelicula pelicula) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pelicula = pelicula;
    }
}