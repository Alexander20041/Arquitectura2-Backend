package com.example.FinazApp.controladores;

import com.example.FinazApp.DTOs.LoginRequest;
import com.example.FinazApp.servicios.ServicioLogin;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/Finanzapp")

public class ControladorLogin {

    //Inyección de dependencias
    private final ServicioLogin servicioLogin;

    @PostMapping("/login")
    public ResponseEntity<?> AutenticarUsuario(@RequestBody LoginRequest loginRequest) {

        Map<String, Object> response = servicioLogin.AutenticarUsuario(loginRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
