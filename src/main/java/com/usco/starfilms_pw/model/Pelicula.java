package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa una película en el sistema.
 * Esta clase almacena información detallada sobre las películas, como su nombre, fecha de estreno, sinopsis,
 * clasificación, idioma, reparto, duración, género y enlaces a su poster e tráiler.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "peliculas")
public class Pelicula {

    /**
     * Identificador único de la película.
     * Este campo se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pelicula_id")
    private Long peliculaId;

    /**
     * Nombre de la película.
     * Este campo no puede ser nulo y debe tener una longitud máxima de 60 caracteres.
     * Solo se permiten letras y espacios.
     */
    @Column(nullable = false, length = 255)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]{1,60}$", message = "letras, espacios y máx 60 caracteres")
    private String nombre;

    /**
     * Fecha de estreno de la película.
     * Este campo puede ser nulo.
     */
    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    /**
     * Sinopsis de la película.
     * Este campo no puede ser nulo y debe tener una longitud máxima de 500 caracteres.
     * Solo se permiten letras, números, espacios y puntuación.
     */
    @Column(nullable = false, length = 500)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9\\s.,!?\"'()\\-:;]{1,500}$", message = "Debe contener letras, números (opcionales), espacios y puntuación, máximo de 500 caracteres.")
    private String sinopsis;

    /**
     * Clasificación de la película.
     * Este campo no puede ser nulo y debe tener una longitud máxima de 100 caracteres.
     * Solo se permiten letras, números y espacios.
     */
    @Column(nullable = false, length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$", message = "La clasificación debe contener solo letras, números y espacios.")
    private String clasificacion;

    /**
     * Idioma de la película.
     * Este campo no puede ser nulo y debe tener una longitud máxima de 50 caracteres.
     * Se permite una descripción entre paréntesis.
     */
    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^(?=.{1,50}$)[a-zA-ZÀ-ÿ]+(\\s*[a-zA-ZÀ-ÿ]+)*\\s*(\\([a-zA-ZÀ-ÿ\\s]+\\))?$", message = "Nombre válido y puede incluir una descripción entre paréntesis, Máximo de 50 caracteres.")
    private String idioma;

    /**
     * Ruta del poster de la película.
     * Este campo no puede ser nulo.
     */
    @Column(name = "ruta_poster", nullable = false)
    private String image;

    /**
     * URL del tráiler de la película en YouTube.
     * Este campo no puede ser nulo y debe seguir el formato adecuado de URL de YouTube.
     */
    @Column(name = "ruta_trailer", nullable = false)
    @Pattern(regexp = "^(https?://)?(www\\.)?(youtube\\.com/watch\\?v=|youtu\\.be/)([a-zA-Z0-9_-]{11})(\\S*)?$", message = "Proporcione un URL de youtube valido")
    private String rutaTrailer;

    /**
     * Reparto de la película.
     * Este campo no puede ser nulo y debe tener una longitud máxima de 500 caracteres.
     * Solo se permiten letras, números, espacios y puntuación.
     */
    @Column(nullable = false, length = 500)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9\\s.,!?\"'()\\-:;]{1,500}$", message = "El Reparto puede contener letras, espacios, puntuación y tener un máximo de 500 caracteres.")
    private String reparto;

    /**
     * Duración de la película en minutos.
     * Este campo no puede ser nulo y debe ser un número entero de hasta 3 dígitos.
     */
    @Column(nullable = false)
    @Digits(integer=3, fraction=0, message = "Entero de máximo 3 dígitos")
    private Integer duracion;

    /**
     * Género de la película.
     * Este campo no puede ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero genero;

    /**
     * Constructor de la clase Pelicula.
     * Este constructor permite la creación de una instancia de Pelicula con todos los campos inicializados.
     *
     * @param nombre        Nombre de la película.
     * @param fechaEstreno  Fecha de estreno de la película.
     * @param sinopsis      Sinopsis de la película.
     * @param clasificacion Clasificación de la película.
     * @param idioma        Idioma de la película.
     * @param image         Ruta del poster de la película.
     * @param rutaTrailer   URL del tráiler de la película.
     * @param reparto       Reparto de la película.
     * @param duracion      Duración de la película en minutos.
     * @param genero        Género de la película.
     */
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
