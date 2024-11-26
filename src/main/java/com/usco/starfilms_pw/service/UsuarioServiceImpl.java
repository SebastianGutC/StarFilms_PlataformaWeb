package com.usco.starfilms_pw.service;

import com.usco.starfilms_pw.dto.UsuarioRegistroDTO;
import com.usco.starfilms_pw.model.Rol;
import com.usco.starfilms_pw.model.Usuario;
import com.usco.starfilms_pw.repository.RolRepository;
import com.usco.starfilms_pw.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio {@link UsuarioService} que maneja la lógica de negocio relacionada con los usuarios.
 * Esta clase proporciona métodos para registrar nuevos usuarios, buscar usuarios por correo electrónico,
 * cargar usuarios a través de su nombre de usuario (correo electrónico), y mapear roles a autoridades de Spring Security.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    private RolRepository rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor que inicializa las dependencias de {@code UsuarioServiceImpl}.
     * @param usuarioRepositorio el repositorio de usuarios.
     * @param rolRepositorio el repositorio de roles.
     */
    public UsuarioServiceImpl(UsuarioRepository usuarioRepositorio, RolRepository rolRepositorio) {
        super();
        this.usuarioRepositorio = usuarioRepositorio;
        this.rolRepositorio = rolRepositorio;
    }

    /**
     * Guarda un nuevo usuario en el sistema.
     * Este método verifica si el correo electrónico del usuario ya está registrado. Si es así, lanza una excepción {@link UsuarioExistenteException}.
     * Si no, crea un nuevo usuario, asigna el rol de "ROLE_MIEMBRO" y guarda el usuario en la base de datos.
     * @param usuarioRegistroDTO el DTO que contiene los datos del usuario a registrar.
     * @return el usuario guardado.
     * @throws UsuarioExistenteException si el correo del usuario ya está registrado.
     */
    @Override
    public Usuario guardar(UsuarioRegistroDTO usuarioRegistroDTO) throws UsuarioExistenteException {
        if(usuarioRepositorio.findByCorreo(usuarioRegistroDTO.getCorreo()) != null) {
            throw new UsuarioExistenteException("El usuario ya existe");
        }
        Rol userRole = rolRepositorio.findByNombre("ROLE_MIEMBRO");
        if (userRole == null) {
            throw new RuntimeException("El rol MIEMBRO no se encuentra en la base de datos");
        }

        Usuario usuario = new Usuario(
                usuarioRegistroDTO.getIdentificacion(),
                usuarioRegistroDTO.getNombre(),
                usuarioRegistroDTO.getApellido(),
                usuarioRegistroDTO.getCorreo(),
                usuarioRegistroDTO.getFechaNacimiento(),
                usuarioRegistroDTO.getEdad(),
                passwordEncoder.encode(usuarioRegistroDTO.getPassword()),
                Arrays.asList(userRole));
        try{
            Usuario savedUser = usuarioRepositorio.save(usuario);
            System.out.println("Usuario guardado: " + savedUser);
            return savedUser;
        } catch (Exception e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
            throw new RuntimeException("Error al guardar el usuario", e);
        }
    }

    /**
     * Busca un usuario por su correo electrónico.
     * @param correo el correo electrónico del usuario a buscar.
     * @return el usuario encontrado, o {@code null} si no existe.
     */
    @Override
    public Usuario findByCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    /**
     * Encuentra todos los usuarios que tienen un rol específico.
     * @param role el nombre del rol a buscar.
     * @return una lista de usuarios con el rol especificado.
     */
    public List<Usuario> findAllByRole(String role) {
        return usuarioRepositorio.findAllByRole(role);
    }

    /**
     * Carga un usuario por su nombre de usuario (correo electrónico).
     * Este método es utilizado por Spring Security para autenticar a un usuario. Si el usuario no existe,
     * se lanza una excepción {@link UsernameNotFoundException}.
     * @param correo el correo electrónico del usuario.
     * @return un {@link UserDetails} que representa el usuario.
     * @throws UsernameNotFoundException si el usuario no se encuentra.
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        System.out.println("Buscando usuario con correo: " + correo);

        Usuario usuario = usuarioRepositorio.findByCorreo(correo);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        System.out.println(usuario);

        return new User(usuario.getCorreo(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    /**
     * Mapea una colección de roles a autoridades de Spring Security.
     * Convierte los roles del usuario en {@link GrantedAuthority}, que es lo que utiliza Spring Security
     * para otorgar permisos y roles a los usuarios durante el proceso de autenticación.
     * @param roles la colección de roles del usuario.
     * @return una lista de {@link GrantedAuthority} correspondiente a los roles del usuario.
     */
    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());
    }
}