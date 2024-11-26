package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Cartelera;
import com.usco.starfilms_pw.model.Pelicula;

import java.util.List;
import java.util.Optional;

public interface CarteleraService {
     public void saveCartelera(Cartelera cartelera);

     public List<Cartelera> getCarteleraActual();

     public List<Cartelera> getCarteleraProximamente();

     Optional<Cartelera> getCarteleraById(Long id);

     Cartelera updateCartelera(Cartelera cartelera);

     public void deleteCartelera(Long id);
}
