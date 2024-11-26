package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Pelicula;

import java.util.List;
import java.util.Optional;

public interface PeliculaService {
    void savePelicula(Pelicula pelicula);
    List<Pelicula> getAllPeliculas();
    public Optional<Pelicula> getPeliculaById(Long id);
    Pelicula listById(Long id);
}
