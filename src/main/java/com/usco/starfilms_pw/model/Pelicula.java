package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pelicula_id")
    private Long peliculaId;

    @Column(nullable = false, length = 255)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]{1,60}$", message = "letras, espacios y máx 60 caracteres")
    private String nombre;

    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    @Column(nullable = false, length = 500)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9\\s.,!?\"'()\\-:;]{1,500}$", message = "Debe contener letras, números (opcionales), espacios y puntuación, máximo de 500 caracteres.")
    private String sinopsis;

    @Column(nullable = false, length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$", message = "La clasificación debe contener solo letras, números y espacios.")
    private String clasificacion;

    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^(?=.{1,50}$)[a-zA-ZÀ-ÿ]+(\\s*[a-zA-ZÀ-ÿ]+)*\\s*(\\([a-zA-ZÀ-ÿ\\s]+\\))?$", message = "Nombre válido y puede incluir una descripción entre paréntesis, Máximo de 50 caracteres.")
    private String idioma;

    @Column(name = "ruta_poster", nullable = false)
    private String image;

    @Column(name = "ruta_trailer", nullable = false)
    @Pattern(regexp = "^(https?://)?(www\\.)?(youtube\\.com/watch\\?v=|youtu\\.be/)([a-zA-Z0-9_-]{11})(\\S*)?$", message = "Proporcione un URL de youtube valido")
    private String rutaTrailer;

    @Column(nullable = false, length = 500)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9\\s.,!?\"'()\\-:;]{1,500}$", message = "El Reparto puede contener letras, espacios, puntuación y tener un máximo de 500 caracteres.")
    private String reparto;

    @Column(nullable = false)
    @Digits(integer=3, fraction=0, message = "Entero de máximo 3 dígitos")
    private Integer duracion;

    @ManyToOne
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero genero;

    public Pelicula(String nombre, LocalDate fechaEstreno, String sinopsis, String clasificacion, String idioma, String image, String rutaTrailer, String reparto, Integer duracion, Genero genero) {
        this.nombre = nombre;
        this.fechaEstreno = fechaEstreno;
        this.sinopsis = sinopsis;
        this.clasificacion = clasificacion;
        this.idioma = idioma;
        this.image = image;
        this.rutaTrailer = rutaTrailer;
        this.reparto = reparto;
        this.duracion = duracion;
        this.genero = genero;
    }
}
