package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Representa a un usuario en el sistema.
 * Esta clase almacena los datos personales del usuario, como la identificación, nombre, correo y fecha de nacimiento,
 * así como sus roles asignados, que determinan sus permisos dentro de la aplicación.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    /**
     * Identificador único del usuario.
     * Este campo se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    /**
     * Identificación del usuario.
     * Este campo representa el número de identificación del usuario (por ejemplo, cédula o pasaporte).
     */
    private Long identificacion;

    /**
     * Nombre del usuario.
     * Este campo almacena el primer nombre del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     * Este campo almacena el apellido del usuario.
     */
    private String apellido;

    /**
     * Correo electrónico del usuario.
     * Este campo almacena la dirección de correo electrónico del usuario, que se utiliza para iniciar sesión.
     */
    private String correo;

    /**
     * Fecha de nacimiento del usuario.
     * Este campo almacena la fecha en que nació el usuario, que puede ser usada para calcular su edad.
     */
    private LocalDate fechaNacimiento;

    /**
     * Edad del usuario.
     * Este campo almacena la edad calculada del usuario, que se obtiene de la fecha de nacimiento.
     */
    private Integer edad;

    /**
     * Contraseña del usuario.
     * Este campo almacena la contraseña cifrada del usuario para la autenticación.
     */
    private String password;

    /**
     * Roles asignados al usuario.
     * Este campo contiene una colección de roles que determinan los permisos del usuario en el sistema.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Collection<Rol> roles;

    /**
     * Obtiene los roles asignados al usuario.
     * Este método devuelve la colección de roles que están asociados al usuario.
     *
     * @return Colección de roles del usuario.
     */
    public Collection<Rol> getRoles() {
        return roles;
    }

    /**
     * Constructor de la clase Usuario.
     * Este constructor permite crear una nueva instancia de Usuario con los valores proporcionados.
     *
     * @param identificacion El número de identificación del usuario.
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param correo El correo electrónico del usuario.
     * @param fechaNacimiento La fecha de nacimiento del usuario.
     * @param edad La edad del usuario.
     * @param password La contraseña del usuario.
     * @param roles Los roles asignados al usuario.
     */
    public Usuario(Long identificacion, String nombre, String apellido, String correo, LocalDate fechaNacimiento, Integer edad, String password, Collection<Rol> roles) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.password = password;
        this.roles = roles;
    }
}