package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un rol asignado a los usuarios en el sistema.
 * Esta clase se utiliza para gestionar los diferentes tipos de roles que los usuarios pueden tener,
 * como 'ADMIN', 'MIEMBRO' y 'STELLAR'. Los roles definen los permisos y acciones que un usuario puede realizar.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "roles")
public class Rol {

    /**
     * Identificador único del rol.
     * Este campo se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Long rolId;

    /**
     * Nombre del rol.
     * Este campo almacena el nombre del rol, como 'ADMIN' o 'USER'.
     */
    private String nombre;

    /**
     * Constructor de la clase Rol.
     * Este constructor permite crear una instancia de Rol con el nombre proporcionado.
     *
     * @param nombre El nombre del rol, como 'ADMIN' o 'USER'.
     */
    public Rol(String nombre) {
        this.nombre = nombre;
    }
}