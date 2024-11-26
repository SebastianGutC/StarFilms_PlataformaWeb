package com.usco.starfilms_pw.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para el registro de un usuario en el sistema.
 * Esta clase se utiliza para transferir la información relacionada con el registro de un usuario,
 * incluyendo su identificación, nombre, apellido, correo electrónico, fecha de nacimiento, edad y contraseña.
 * Contiene validaciones para asegurarse de que los datos ingresados son correctos antes de ser procesados por el backend.
 */
@Data
public class UsuarioRegistroDTO {

    /**
     * Identificación del usuario.
     * Debe ser un número entero de hasta 10 dígitos.
     */
    @NotNull(message = "Campo vacío")
    @Digits(integer=10, fraction=0, message = "Entero de máximo 10 dígitos")
    private Long identificacion;

    /**
     * Nombre del usuario.
     * Solo permite letras y espacios, con un máximo de 40 caracteres.
     */
    @NotBlank(message = "Campo vacío")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]{1,40}$", message = "letras, espacios y máx 40 caracteres")
    private String nombre;

    /**
     * Apellido del usuario.
     * Solo permite letras y espacios, con un máximo de 40 caracteres.
     */
    @NotBlank(message = "Campo vacío")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]{1,40}$", message = "letras, espacios y máx 40 caracteres")
    private String apellido;

    /**
     * Correo electrónico del usuario.
     * Debe tener un formato válido de correo electrónico.
     * También se valida con una expresión regular que garantiza el formato correcto.
     */
    @NotBlank(message = "Campo vacío")
    @Email(message = "El correo debe tener un formato válido")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$", message = "Formato de correo inválido")
    private String correo;

    /**
     * Fecha de nacimiento del usuario.
     * No puede ser nula y debe ser una fecha válida.
     */
    @NotNull(message = "Campo vacío")
    private LocalDate fechaNacimiento;

    /**
     * Edad del usuario.
     * Esta propiedad se calcula a partir de la fecha de nacimiento.
     */
    private Integer edad;

    /**
     * Contraseña del usuario.
     * Debe tener entre 4 y 20 caracteres y debe contener al menos una letra mayúscula, una minúscula, un número y un carácter especial.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[._-])[a-zA-Z0-9._-]{4,20}$", message = " 4-20 Mínimo una letra mayúscula, minúscula, número y caracter especial")
    private String password;

    /**
     * Constructor con parámetros para inicializar el DTO de registro de usuario.
     *
     * @param identificacion La identificación del usuario.
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param correo El correo electrónico del usuario.
     * @param fechaNacimiento La fecha de nacimiento del usuario.
     * @param edad La edad del usuario.
     * @param password La contraseña del usuario.
     */
    public UsuarioRegistroDTO(Long identificacion, String nombre, String apellido, String correo, LocalDate fechaNacimiento, Integer edad, String password) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.password = password;
    }

    /**
     * Constructor vacío para inicializar un objeto de tipo UsuarioRegistroDTO.
     */
    public UsuarioRegistroDTO() {
        super();
    }
}