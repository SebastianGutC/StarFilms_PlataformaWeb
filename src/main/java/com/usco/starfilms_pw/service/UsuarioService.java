package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.dto.UsuarioRegistroDTO;
import com.usco.starfilms_pw.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService extends UserDetailsService {

    Usuario findByCorreo(String correo);

    public List<Usuario> findAllByRole(String role);

    public Usuario guardar(UsuarioRegistroDTO RegistroDTO) throws UsuarioExistenteException;

}
