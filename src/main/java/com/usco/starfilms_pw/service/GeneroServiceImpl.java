package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Genero;
import com.usco.starfilms_pw.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para gestionar géneros de películas.
 * Esta clase implementa la interfaz {@link GeneroService} y proporciona la lógica de negocio para interactuar con
 * la base de datos a través del repositorio {@link GeneroRepository}. Permite obtener todos los géneros o un género
 * específico por su ID.
 */
@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    /**
     * Recupera todos los géneros de películas almacenados en la base de datos.
     * Este método consulta el repositorio {@link GeneroRepository} y devuelve una lista con todos los géneros disponibles.
     * @return una lista de objetos {@link Genero} con todos los géneros almacenados en la base de datos.
     */
    @Override
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    /**
     * Recupera un género específico por su ID.
     * <p>
     * Este método consulta el repositorio {@link GeneroRepository} para encontrar un género basado en su ID.
     * Si no se encuentra un género con el ID proporcionado, se lanza una excepción {@link IllegalArgumentException}.
     *
     * @param generoId el ID del género que se desea recuperar.
     * @return un objeto {@link Genero} correspondiente al género con el ID proporcionado.
     * @throws IllegalArgumentException si el género con el ID especificado no existe en la base de datos.
     */
    @Override
    public Genero findById(Long generoId) {
        return generoRepository.findById(generoId)
                .orElseThrow(() -> new IllegalArgumentException("El género no existe"));
    }
}