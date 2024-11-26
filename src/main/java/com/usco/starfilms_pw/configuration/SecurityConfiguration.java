package com.usco.starfilms_pw.configuration;

import com.usco.starfilms_pw.repository.RolRepository;
import com.usco.starfilms_pw.repository.UsuarioRepository;
import com.usco.starfilms_pw.service.CustomOAuth2UserService;
import com.usco.starfilms_pw.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Clase de configuración de seguridad que gestiona la autenticación, autorización y protección
 * contra CSRF para la aplicación web.
 * Esta clase habilita la seguridad en la aplicación usando Spring Security, define un proveedor de autenticación,
        * maneja el login de formularios, configuración de OAuth2, manejo de excepciones y controla los permisos de acceso
 * para diferentes roles de usuario.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    /**
     * Crea un {@link BCryptPasswordEncoder} bean para el cifrado de contraseñas.
     * <p>
     * El {@link BCryptPasswordEncoder} es utilizado para codificar las contraseñas antes de almacenarlas
     * en la base de datos.
     *
     * @return una instancia de {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el {@link DaoAuthenticationProvider} para la autenticación de usuarios.
     * <p>
     * Este proveedor de autenticación usa el {@link UsuarioService} para cargar los detalles del usuario
     * y el {@link BCryptPasswordEncoder} para comparar las contraseñas.
     *
     * @param usuarioServicio servicio que proporciona los detalles de los usuarios para la autenticación
     * @return una instancia configurada de {@link DaoAuthenticationProvider}
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UsuarioService usuarioServicio) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioServicio);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    /**
     * Crea un {@link CustomOAuth2UserService} bean para manejar la autenticación con OAuth2.
     * <p>
     * Este servicio personalizado gestiona la carga y asociación de los usuarios con roles
     * después de la autenticación exitosa usando OAuth2.
     *
     * @param usuarioRepository repositorio de usuarios para acceder a la base de datos
     * @param rolRepository repositorio de roles para asignar roles a los usuarios
     * @return una instancia de {@link CustomOAuth2UserService}
     */
    @Bean
    public CustomOAuth2UserService customOAuth2UserService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        return new CustomOAuth2UserService(usuarioRepository, rolRepository);
    }

    /**
     * Configura la cadena de filtros de seguridad para definir las reglas de acceso a las rutas de la aplicación.
     * <p>
     * Configura la protección contra CSRF, la autorización basada en roles, y el manejo de inicio de sesión,
     * cierre de sesión, y excepciones de acceso denegado.
     *
     * @param http objeto {@link HttpSecurity} usado para configurar la seguridad en la aplicación
     * @return una instancia de {@link SecurityFilterChain} configurada
     * @throws Exception si ocurre un error al configurar la seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/signup", "/membresias", "/boleteria", "/pelicula/**").permitAll()
                        .requestMatchers("/", "/uploads/**" ).permitAll()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/img/**").permitAll() // Permitir acceso a los recursos estáticos
                        .requestMatchers("/addpelicula", "/formpelicula", "/admdetail/", "/updatepelicula/", "/formcartelera/","/addcartelera/","/formproyeccion/","/addproyeccion/").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("correo")
                        .passwordParameter("password")
                        .permitAll()
                        .defaultSuccessUrl("/home", true))
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService(usuarioRepository, rolRepository))))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403")
                );
        return http.build();
    }
}
