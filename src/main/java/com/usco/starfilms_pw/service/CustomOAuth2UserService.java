package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.model.Rol;
import com.usco.starfilms_pw.model.Usuario;
import com.usco.starfilms_pw.repository.RolRepository;
import com.usco.starfilms_pw.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public CustomOAuth2UserService(UsuarioRepository usuarioRepositorio, RolRepository rolRepositorio) {
        this.usuarioRepository = usuarioRepositorio;
        this.rolRepository = rolRepositorio;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("given_name");
        String apellido = oAuth2User.getAttribute("family_name");

        Usuario usuario = usuarioRepository.findByCorreo(email);

        if (usuario == null) {
            // Crear un nuevo usuario si no existe
            Rol userRole = rolRepository.findByNombre("ROLE_MIEMBRO");
            if (userRole == null) {
                throw new RuntimeException("El rol ROLE_MIEMBRO no existe en la base de datos");
            }
            usuario = new Usuario();
            usuario.setCorreo(email);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setPassword("");
            usuario.setRoles(Arrays.asList(userRole));
            usuarioRepository.save(usuario);
        }

        return new DefaultOAuth2User(
                usuario.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).toList(),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}


