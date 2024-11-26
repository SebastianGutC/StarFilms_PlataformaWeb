package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Cartelera;
import com.usco.starfilms_pw.repository.CarteleraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CarteleraServiceImpl implements CarteleraService {

    private final CarteleraRepository carteleraRepository;

    public CarteleraServiceImpl(CarteleraRepository carteleraRepository) {
        this.carteleraRepository = carteleraRepository;
    }

    @Override
    public void saveCartelera(Cartelera cartelera) {
        boolean exists = carteleraRepository.existsByPelicula(cartelera.getPelicula());
        if (exists) {
            throw new RuntimeException("La película ya está en la cartelera.");
        }
        carteleraRepository.save(cartelera);
    }

    @Override
    public List<Cartelera> getCarteleraActual() {
        LocalDate fechaActual = LocalDate.now();
        return carteleraRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                fechaActual, fechaActual
        );
    }

    @Override
    public List<Cartelera> getCarteleraProximamente() {
        LocalDate fechaActual = LocalDate.now();
        return carteleraRepository.findByFechaInicioGreaterThan(fechaActual);
    }

    @Override
    public Optional<Cartelera> getCarteleraById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cartelera updateCartelera(Cartelera cartelera) {
        return null;
    }

    @Override
    public void deleteCartelera(Long id) {
        try {
            if (carteleraRepository.existsById(id)) {
                carteleraRepository.deleteById(id);
            } else {
                throw new RuntimeException("Película en Cartelera no encontrada");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la película de Cartelera: " + e.getMessage());
        }
    }
}
