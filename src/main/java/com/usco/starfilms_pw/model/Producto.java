package com.usco.starfilms_pw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Representa un producto en el sistema.
 * Esta clase almacena información detallada sobre un producto, como su nombre, descripción y precios,
 * tanto para usuarios estándar como para usuarios con membresía Stellar.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productos")
public class Producto {

    /**
     * Identificador único del producto.
     * Este campo se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long productoId;

    /**
     * Nombre del producto.
     * Este campo no puede ser nulo y debe tener una longitud máxima de 150 caracteres.
     */
    @Column(nullable = false, length = 150)
    private String nombre;

    /**
     * Descripción del producto.
     * Este campo no puede ser nulo y contiene información detallada sobre el producto.
     */
    @Column(nullable = false)
    private String descripcion;

    /**
     * Precio del producto para usuarios estándar.
     * Este campo no puede ser nulo y almacena el precio del producto en formato decimal.
     */
    @Column(nullable = false)
    private BigDecimal precio;

    /**
     * Precio del producto para usuarios con membresía Stellar.
     * Este campo no puede ser nulo y almacena el precio con descuento o beneficio para usuarios Stellar.
     */
    @Column(name = "precio_stellar", nullable = false)
    private BigDecimal precioStellar;

    /**
     * Constructor de la clase Producto.
     * Este constructor permite la creación de una instancia de Producto con todos los campos inicializados.
     *
     * @param nombre         Nombre del producto.
     * @param descripcion    Descripción del producto.
     * @param precio         Precio del producto para usuarios estándar.
     * @param precioStellar  Precio del producto para usuarios Stellar.
     */
    public Producto(String nombre, String descripcion, BigDecimal precio, BigDecimal precioStellar) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precioStellar = precioStellar;
    }
}
