package com.example.FinazApp.servicios;

import com.example.FinazApp.DTOs.UsuarioDTO;
import com.example.FinazApp.entidades.ERole;
import com.example.FinazApp.entidades.Roles;
import com.example.FinazApp.entidades.Usuario;
import com.example.FinazApp.repositorios.RepositorioRoles;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
public class ServicioUsuario {

    @Autowired
    RepositorioUsuario repositorioUsuario;  // Repositorio para operaciones CRUD de usuarios
    @Autowired
    RepositorioRoles repositorioRoles; // Repositorio para obtener roles disponibles
    @Autowired
    PasswordEncoder passwordEncoder;

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {

        // Construcción del usuario con patrón Builder
        Usuario nuevoUsuario = Usuario.builder()
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .email(usuarioDTO.getEmail())
                .username(usuarioDTO.getUsername())
                .contrasena(passwordEncoder.encode(usuarioDTO.getContrasena())) // Encriptar
                .build();

        // Asignar roles
        Set<Roles> roles = usuarioDTO.getRoles().stream()
                .map(rol -> repositorioRoles.findByName(ERole.valueOf(rol))
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado")))
                .collect(Collectors.toSet());

        nuevoUsuario.setRoles(roles);

        // Guardar en base de datos
        Usuario usuarioGuardado = repositorioUsuario.save(nuevoUsuario);

        // Retornar manualmente el DTO (sin ModelMapper)
        UsuarioDTO usuarioGuardadoDTO = UsuarioDTO.builder()
                .id(usuarioGuardado.getId())
                .nombre(usuarioGuardado.getNombre())
                .apellido(usuarioGuardado.getApellido())
                .email(usuarioGuardado.getEmail())
                .username(usuarioGuardado.getUsername())
                .roles(usuarioGuardado.getRoles().stream()
                        .map(rol -> rol.getName().name())
                        .collect(Collectors.toSet()))
                .build();

        return usuarioGuardadoDTO;
    }



}
