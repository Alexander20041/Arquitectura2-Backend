package com.example.FinazApp.servicios;

import com.example.FinazApp.DTOs.LoginRequest;
import com.example.FinazApp.config.JwtUtils;
import com.example.FinazApp.entidades.Usuario;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ServicioLogin {

    // Inyección de dependencias
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RepositorioUsuario repositorioUsuario;

    public Map<String, Object> AutenticarUsuario(LoginRequest loginRequest) {
        // 1. Obtener el usuario desde la base de datos
        Usuario usuario = repositorioUsuario.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("El username proporcionado no existe"));

        // 2. Intentar autenticar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getContrasena()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generar el token JWT
        String jwt = jwtUtils.generateToken(usuario.getId_usuario(), usuario.getUsername());

        // 4. Construir la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Autenticación exitosa");
        response.put("id", usuario.getId_usuario());
        response.put("nombre", usuario.getUsername());
        response.put("token", jwt);

        return response;
    }
}
