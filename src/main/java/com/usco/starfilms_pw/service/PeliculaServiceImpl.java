package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Pelicula;
import com.usco.starfilms_pw.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementación del servicio para gestionar las películas.
 * <p>
 * Esta clase implementa la interfaz {@link PeliculaService} y proporciona la lógica de negocio para interactuar con
 * la base de datos a través del repositorio {@link PeliculaRepository}. Permite realizar operaciones como guardar,
 * obtener todas las películas, y buscar una película específica por su ID.
 */
@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    /**
     * Guarda una nueva película en la base de datos.
     * <p>
     * Este método utiliza el repositorio {@link PeliculaRepository} para guardar la película proporcionada en la base de datos.
     *
     * @param pelicula el objeto {@link Pelicula} que se desea guardar en la base de datos.
     */
    @Override
    public void savePelicula(Pelicula pelicula) {
        peliculaRepository.save(pelicula);
    }

    /**
     * Recupera todas las películas almacenadas en la base de datos.
     * <p>
     * Este método consulta el repositorio {@link PeliculaRepository} y devuelve una lista con todas las películas disponibles.
     *
     * @return una lista de objetos {@link Pelicula} con todas las películas almacenadas en la base de datos.
     */
    @Override
    public List<Pelicula> getAllPeliculas() {
        return peliculaRepository.findAll();
    }

    /**
     * Recupera una película específica por su ID.
     * <p>
     * Este método consulta el repositorio {@link PeliculaRepository} para encontrar una película basada en su ID.
     * Si no se encuentra una película con el ID proporcionado, se devuelve un {@link Optional} vacío.
     *
     * @param id el ID de la película que se desea recuperar.
     * @return un {@link Optional<Pelicula>} con la película correspondiente al ID proporcionado.
     */
    @Override
    public Optional<Pelicula> getPeliculaById(Long id) {
        return peliculaRepository.findById(id);
    }

    /**
     * Recupera una película específica por su ID y la devuelve directamente.
     * <p>
     * Este método consulta el repositorio {@link PeliculaRepository} y obtiene la película correspondiente al ID proporcionado.
     * Si no se encuentra la película, se lanza una excepción {@link NoSuchElementException}.
     *
     * @param id el ID de la película que se desea recuperar.
     * @return la película correspondiente al ID proporcionado.
     * @throws NoSuchElementException si no se encuentra una película con el ID especificado.
     */
    @Override
    public Pelicula listById(Long id) {
        return peliculaRepository.findById(id).get();
    }
}