package com.example.FinazApp.config;

import com.example.FinazApp.config.JwtAutenticationFilter;
import com.example.FinazApp.config.JwtAuthorizationFilter;
import com.example.FinazApp.config.JwtUtils;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import com.example.FinazApp.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Lazy
    @Autowired
    ServicioUsuario userDetailsService;

    @Lazy
    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity , AuthenticationManager authenticationManager) throws Exception {

        JwtAutenticationFilter jwtAutenticationFilter = new JwtAutenticationFilter();
        jwtAutenticationFilter.setAuthenticationManager(authenticationManager);


        return httpSecurity
                .csrf().disable() // Deshabilita CSRF
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().permitAll(); // Permite todas las solicitudes sin autenticación
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sesiones sin estado
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity , PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();

    }

}
