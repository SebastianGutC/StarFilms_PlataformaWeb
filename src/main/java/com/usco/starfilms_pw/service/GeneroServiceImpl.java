package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Genero;
import com.usco.starfilms_pw.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    @Override
    public Genero findById(Long generoId) {
        return generoRepository.findById(generoId)
                .orElseThrow(() -> new IllegalArgumentException("El género no existe"));
    }
}
