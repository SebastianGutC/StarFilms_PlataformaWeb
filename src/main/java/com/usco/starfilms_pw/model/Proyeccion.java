package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "proyecciones")
public class Proyeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyeccion_id")
    private Long proyeccionId;

    @Column(name = "fecha_proyeccion", nullable = false)
    private LocalDate fechaProyeccion;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(name = "precio_stellar", nullable = false)
    private BigDecimal precioStellar;

    @Column(name = "hora_inicio",nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin",nullable = false)
    private LocalTime horaFin;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "sala_id",nullable = false)
    private Sala sala;

    public Proyeccion( LocalDate fechaProyeccion, BigDecimal precio, BigDecimal precioStellar, LocalTime horaInicio, LocalTime horaFin, Pelicula pelicula, Sala sala) {
        this.fechaProyeccion = fechaProyeccion;
        this.precio = precio;
        this.precioStellar = precioStellar;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.pelicula = pelicula;
        this.sala = sala;
    }

}
