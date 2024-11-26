package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Sala;
import com.usco.starfilms_pw.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaServiceImpl implements SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Override
    public List<Sala> getAllSalas() {
        return salaRepository.findAll();
    }

}
