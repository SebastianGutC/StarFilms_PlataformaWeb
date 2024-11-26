package com.usco.starfilms_pw.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long salaId;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(name ="tipo_sala", nullable = false, length = 150)
    private String tipoSala;

    public Sala(String nombre, String tipoSala) {
        this.nombre = nombre;
        this.tipoSala = tipoSala;
    }
}
