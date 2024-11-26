package com.usco.starfilms_pw.repository;

import com.usco.starfilms_pw.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    @Override
    Optional<Genero> findById(Long id);

    @Query("SELECT g FROM Genero g WHERE g.genero = :genero")
    Genero findByGenero(String genero);
}
