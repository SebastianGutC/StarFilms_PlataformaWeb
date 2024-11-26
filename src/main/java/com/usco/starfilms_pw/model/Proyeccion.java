package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa una proyección de película en un cine.
 * Esta clase almacena la información de la proyección, como la fecha, las horas de inicio y fin,
 * los precios para usuarios estándar y usuarios Stellar, y la película y la sala asociada a la proyección.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "proyecciones")
public class Proyeccion {

    /**
     * Identificador único de la proyección.
     * Este campo se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyeccion_id")
    private Long proyeccionId;

    /**
     * Fecha en que se realiza la proyección de la película.
     * Este campo no puede ser nulo.
     */
    @Column(name = "fecha_proyeccion", nullable = false)
    private LocalDate fechaProyeccion;

    /**
     * Precio de la proyección para usuarios estándar.
     * Este campo no puede ser nulo y almacena el precio de la proyección en formato decimal.
     */
    @Column(nullable = false)
    private BigDecimal precio;

    /**
     * Precio de la proyección para usuarios con membresía Stellar.
     * Este campo no puede ser nulo y almacena el precio con descuento o beneficio para usuarios Stellar.
     */
    @Column(name = "precio_stellar", nullable = false)
    private BigDecimal precioStellar;

    /**
     * Hora de inicio de la proyección.
     * Este campo no puede ser nulo.
     */
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    /**
     * Hora de finalización de la proyección.
     * Este campo no puede ser nulo.
     */
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    /**
     * Película asociada a la proyección.
     * Este campo no puede ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    /**
     * Sala donde se lleva a cabo la proyección.
     * Este campo no puede ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    /**
     * Constructor de la clase Proyeccion.
     * Este constructor permite la creación de una instancia de Proyeccion con todos los campos inicializados.
     *
     * @param fechaProyeccion  Fecha de la proyección.
     * @param precio           Precio de la proyección para usuarios estándar.
     * @param precioStellar    Precio con descuento para usuarios Stellar.
     * @param horaInicio       Hora de inicio de la proyección.
     * @param horaFin          Hora de finalización de la proyección.
     * @param pelicula         Película asociada a la proyección.
     * @param sala             Sala donde se lleva a cabo la proyección.
     */
    public Proyeccion(LocalDate fechaProyeccion, BigDecimal precio, BigDecimal precioStellar,
                      LocalTime horaInicio, LocalTime horaFin, Pelicula pelicula, Sala sala) {
        this.fechaProyeccion = fechaProyeccion;
        this.precio = precio;
        this.precioStellar = precioStellar;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.pelicula = pelicula;
        this.sala = sala;
    }
}