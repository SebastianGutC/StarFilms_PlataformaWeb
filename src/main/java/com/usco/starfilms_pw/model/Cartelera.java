package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cartelera")
public class Cartelera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carteleraId;

    @Column(name = "fecha_inicio",nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin",nullable = false)
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    public Cartelera(LocalDate fechaInicio, LocalDate fechaFin, Pelicula pelicula) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pelicula = pelicula;
    }
}
