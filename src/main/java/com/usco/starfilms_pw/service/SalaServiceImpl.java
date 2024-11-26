package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Sala;
import com.usco.starfilms_pw.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para gestionar las salas de cine.
 * Esta clase implementa la interfaz {@link SalaService} y proporciona la lógica de negocio para interactuar con
 * el repositorio {@link SalaRepository}. Permite obtener una lista de todas las salas de cine registradas en la base de datos.
 */
@Service
public class SalaServiceImpl implements SalaService {

    @Autowired
    private SalaRepository salaRepository;

    /**
     * Obtiene todas las salas de cine registradas en la base de datos.
     * Este método consulta todas las salas disponibles en la base de datos utilizando el repositorio {@link SalaRepository}.
     * @return una lista de todas las salas registradas.
     */
    @Override
    public List<Sala> getAllSalas() {
        return salaRepository.findAll();
    }
}
