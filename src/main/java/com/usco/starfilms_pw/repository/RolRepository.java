package com.usco.starfilms_pw.repository;

import com.usco.starfilms_pw.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    @Query("SELECT r FROM Rol r WHERE r.nombre = :nombre")
    Rol findByNombre(String nombre);


}
