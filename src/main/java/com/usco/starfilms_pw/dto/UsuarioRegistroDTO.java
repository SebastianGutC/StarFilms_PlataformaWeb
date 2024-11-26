package com.usco.starfilms_pw.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioRegistroDTO {

    @NotNull(message = "Campo vacío")
    @Digits(integer=10, fraction=0, message = "Entero de máximo 10 dígitos")
    private Long identificacion;

    @NotBlank(message = "Campo vacío")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]{1,40}$", message = "letras, espacios y máx 40 caracteres")
    private String nombre;

    @NotBlank(message = "Campo vacío")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]{1,40}$", message = "letras, espacios y máx 40 caracteres")
    private String apellido;

    @NotBlank(message = "Campo vacío")
    @Email(message = "El correo debe tener un formato válido")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$", message = "Formato de correo inválido")
    private String correo;

    @NotNull(message = "Campo vacío")
    private LocalDate fechaNacimiento;

    private Integer edad;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[._-])[a-zA-Z0-9._-]{4,20}$", message = " 4-20 Mínimo una letra mayúscula, minúscula, número y caracter especial")
    private String password;

    public UsuarioRegistroDTO(Long identificacion, String nombre, String apellido, String correo, LocalDate fechaNacimiento, Integer edad, String password) {

        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.password = password;
    }

    public UsuarioRegistroDTO() {
        super();
    }

}
