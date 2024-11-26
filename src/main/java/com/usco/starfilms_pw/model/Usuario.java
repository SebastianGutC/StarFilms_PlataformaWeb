package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    private Long identificacion;

    private String nombre;

    private String apellido;

    private String correo;

    private LocalDate fechaNacimiento;

    private Integer edad;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Collection<Rol> roles;


    public Collection<Rol> getRoles() {
        return roles;
    }

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
