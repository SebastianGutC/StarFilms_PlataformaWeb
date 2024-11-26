package com.usco.starfilms_pw.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una sala de cine en el sistema.
 * Esta clase almacena los datos relacionados con las salas donde se proyectan las películas,
 * como el nombre de la sala y el tipo de sala.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "salas")
public class Sala {

    /**
     * Identificador único de la sala.
     * Este campo se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long salaId;

    /**
     * Nombre de la sala.
     * Este campo almacena el nombre de la sala, como 'Sala 1'.
     */
    @Column(nullable = false, length = 150)
    private String nombre;

    /**
     * Tipo de la sala.
     * Este campo describe el tipo de la sala, como '3D' y '2D'.
     */
    @Column(name ="tipo_sala", nullable = false, length = 150)
    private String tipoSala;

    /**
     * Constructor de la clase Sala.
     * Este constructor permite crear una instancia de Sala con el nombre y tipo de sala proporcionados.
     *
     * @param nombre El nombre de la sala, como 'Sala 1','Sala 2',etc.
     * @param tipoSala El tipo de la sala, como '3D' y '2D'.
     */
    public Sala(String nombre, String tipoSala) {
        this.nombre = nombre;
        this.tipoSala = tipoSala;
    }
}
