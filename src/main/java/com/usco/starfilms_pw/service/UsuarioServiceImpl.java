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

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    private RolRepository rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepositorio, RolRepository rolRepositorio) {
        super();
        this.usuarioRepositorio = usuarioRepositorio;
        this.rolRepositorio = rolRepositorio;
    }

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

    @Override
    public Usuario findByCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    public List<Usuario> findAllByRole(String role) {
        return usuarioRepositorio.findAllByRole(role);
    }

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

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());
    }
}
