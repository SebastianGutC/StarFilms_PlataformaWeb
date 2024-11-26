package com.usco.starfilms_pw.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un género de película en el sistema.
 * Esta clase almacena la información sobre los géneros disponibles que pueden ser asignados a las películas.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "generos")
public class Genero {

    /**
     * Identificador único del género.
     * Este campo se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long generoId;

    /**
     * Nombre del género de la película.
     * Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private String genero;
}
