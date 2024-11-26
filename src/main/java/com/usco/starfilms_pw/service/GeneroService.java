package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Genero;

import java.util.List;

public interface GeneroService {
    
    List<Genero> findAll();

    Genero findById(Long generoId);
}

