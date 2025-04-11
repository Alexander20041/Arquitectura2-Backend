package com.example.FinazApp.controladores;

import com.example.FinazApp.DTOs.UsuarioDTO;
import com.example.FinazApp.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Finanzapp")
public class ControladorUsuario {

    //Inyección de dependencias
    @Autowired
    ServicioUsuario servicioUsuario;

    //Primera historia de usuario: Registro

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO usuario) {

        UsuarioDTO usuarioinsertado = servicioUsuario.registrarUsuario(usuario);

        if (usuarioinsertado != null) {
            return ResponseEntity.ok(usuarioinsertado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
