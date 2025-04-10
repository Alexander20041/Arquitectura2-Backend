package com.example.FinazApp.DTOs;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;
    private String username;
    private String nombre;
    private String email;
    private String apellido;
    private String contrasena;
    private Set<String> roles;

}
