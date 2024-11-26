package com.usco.starfilms_pw.repository;

import com.usco.starfilms_pw.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsuarioId(Long usuarioId);

    Usuario findByCorreo(String correo);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombre = :role")
    List<Usuario> findAllByRole(String role);
}
