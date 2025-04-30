package com.example.FinazApp.controladores;

import com.example.FinazApp.DTOs.UsuarioDTO;
import com.example.FinazApp.servicios.ServicioUsuario;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/Finanzapp")
public class ControladorUsuario {

    @Autowired
    ServicioUsuario servicioUsuario;

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
