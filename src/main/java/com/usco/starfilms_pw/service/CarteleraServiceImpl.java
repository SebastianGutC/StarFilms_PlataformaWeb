package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Cartelera;
import com.usco.starfilms_pw.repository.CarteleraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio {@link CarteleraService} que gestiona las operaciones relacionadas con la cartelera de películas.
 * Esta clase se encarga de gestionar las funciones básicas de la cartelera, como guardar una película en la cartelera,
 * obtener las películas actuales y las que se proyectarán próximamente, y eliminar películas de la cartelera.
 * Además, implementa la validación para asegurar que no se agregue una película repetida en la cartelera.
 */
@Service
public class CarteleraServiceImpl implements CarteleraService {

    private final CarteleraRepository carteleraRepository;

    /**
     * Constructor de la clase {@link CarteleraServiceImpl}.
     * <p>
     * Este constructor recibe un {@link CarteleraRepository} para interactuar con la base de datos.
     *
     * @param carteleraRepository el repositorio de {@link Cartelera} para acceder y manipular los datos en la base de datos
     */
    public CarteleraServiceImpl(CarteleraRepository carteleraRepository) {
        this.carteleraRepository = carteleraRepository;
    }

    /**
     * Guarda una nueva película en la cartelera.
     * Si la película ya existe en la cartelera, se lanza una excepción {@link RuntimeException}.
     * @param cartelera objeto {@link Cartelera} que representa la película a guardar
     * @throws RuntimeException si la película ya está en la cartelera
     */
    @Override
    public void saveCartelera(Cartelera cartelera) {
        boolean exists = carteleraRepository.existsByPelicula(cartelera.getPelicula());
        if (exists) {
            throw new RuntimeException("La película ya está en la cartelera.");
        }
        carteleraRepository.save(cartelera);
    }

    /**
     * Obtiene las películas actuales en la cartelera que están en proyección.
     * La cartelera actual es aquella cuya fecha de inicio es anterior o igual a la fecha actual,
     * y cuya fecha de fin es posterior o igual a la fecha actual.
     * @return una lista de películas de tipo {@link Cartelera} que están en proyección actualmente
     */
    @Override
    public List<Cartelera> getCarteleraActual() {
        LocalDate fechaActual = LocalDate.now();
        return carteleraRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                fechaActual, fechaActual
        );
    }

    /**
     * Obtiene las películas que estarán próximamente en la cartelera.
     * Las películas próximas son aquellas cuya fecha de inicio es posterior a la fecha actual.
     * @return una lista de películas de tipo {@link Cartelera} que se proyectarán próximamente
     */
    @Override
    public List<Cartelera> getCarteleraProximamente() {
        LocalDate fechaActual = LocalDate.now();
        return carteleraRepository.findByFechaInicioGreaterThan(fechaActual);
    }

    /**
     * Obtiene una película de la cartelera por su ID.
     * Este método está actualmente vacío y debe ser implementado si se requiere la funcionalidad.
     * @param id el ID de la película en la cartelera
     * @return un {@link Optional} que contiene la película si existe, o está vacío si no se encuentra
     */
    @Override
    public Optional<Cartelera> getCarteleraById(Long id) {
        return Optional.empty();
    }

    /**
     * Actualiza los detalles de una película en la cartelera.
     * Este método está actualmente vacío y debe ser implementado si se requiere la funcionalidad.
     * @param cartelera el objeto {@link Cartelera} con los nuevos detalles de la película
     * @return el objeto {@link Cartelera} actualizado
     */
    @Override
    public Cartelera updateCartelera(Cartelera cartelera) {
        return null;
    }

    /**
     * Elimina una película de la cartelera por su ID.
     * Si la película no se encuentra, se lanza una excepción {@link RuntimeException}.
     * @param id el ID de la película a eliminar
     * @throws RuntimeException si ocurre un error al eliminar la película o si la película no se encuentra
     */
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
