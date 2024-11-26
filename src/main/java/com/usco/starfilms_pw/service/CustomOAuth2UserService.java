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

/**
 * Servicio personalizado para cargar y gestionar los usuarios autenticados mediante OAuth2.
 * Esta clase extiende {@link DefaultOAuth2UserService} y sobrescribe el método {@link #loadUser(OAuth2UserRequest)}
 * para personalizar la carga de usuarios desde un proveedor OAuth2. Si un usuario no existe en la base de datos,
 * se crea uno nuevo con un rol predeterminado.
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    /**
     * Constructor para inicializar el servicio {@link CustomOAuth2UserService}.
     * <p>
     * Este constructor recibe los repositorios de {@link Usuario} y {@link Rol} para interactuar con la base de datos.
     *
     * @param usuarioRepositorio el repositorio de {@link Usuario} para gestionar la información de los usuarios
     * @param rolRepositorio el repositorio de {@link Rol} para gestionar los roles de los usuarios
     */
    public CustomOAuth2UserService(UsuarioRepository usuarioRepositorio, RolRepository rolRepositorio) {
        this.usuarioRepository = usuarioRepositorio;
        this.rolRepository = rolRepositorio;
    }

    /**
     * Sobrescribe el método {@link DefaultOAuth2UserService#loadUser(OAuth2UserRequest)} para personalizar la carga de
     * un usuario autenticado mediante OAuth2. Si el usuario no existe en la base de datos, se crea un nuevo usuario
     * con los atributos proporcionados por el proveedor OAuth2 y se le asigna el rol predeterminado "ROLE_MIEMBRO".
     * Además, este método devuelve un {@link DefaultOAuth2User} que incluye los roles del usuario y sus atributos
     * provenientes del proveedor OAuth2.
     * @param userRequest objeto que contiene la solicitud de autenticación del usuario mediante OAuth2
     * @return un {@link OAuth2User} que representa al usuario autenticado con sus roles y atributos
     * @throws OAuth2AuthenticationException si ocurre un error al autenticar al usuario con OAuth2
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Recuperar atributos del usuario desde el proveedor OAuth2
        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("given_name");
        String apellido = oAuth2User.getAttribute("family_name");

        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(email);

        // Si el usuario no existe, crear uno nuevo
        if (usuario == null) {
            // Buscar el rol predeterminado "ROLE_MIEMBRO"
            Rol userRole = rolRepository.findByNombre("ROLE_MIEMBRO");
            if (userRole == null) {
                throw new RuntimeException("El rol ROLE_MIEMBRO no existe en la base de datos");
            }

            // Crear un nuevo usuario con los datos obtenidos del proveedor OAuth2
            usuario = new Usuario();
            usuario.setCorreo(email);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setPassword("");  // La contraseña se deja vacía ya que es gestionada por el proveedor OAuth2
            usuario.setRoles(Arrays.asList(userRole));

            // Guardar el usuario en la base de datos
            usuarioRepository.save(usuario);
        }

        // Devolver un objeto OAuth2User con los roles y atributos del usuario
        return new DefaultOAuth2User(
                usuario.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).toList(),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}


