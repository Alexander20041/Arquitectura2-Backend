package com.example.FinazApp.controladores;


import com.example.FinazApp.DTOs.AlertaDTO;
import com.example.FinazApp.servicios.ServicioAlerta;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/Finanzapp/Alertas")
public class ControladorAlerta {

    //Inyección de dependencias
    private final ServicioAlerta servicioAlerta;


    @PostMapping("/RegistrarAlerta/{id_usuario}")
    public ResponseEntity<AlertaDTO> registrarAlerta(@RequestBody AlertaDTO alerta, @PathVariable Long id_usuario) {
        AlertaDTO alertaRegistrada = servicioAlerta.RegistrarAlerta(alerta, id_usuario);
        return (alertaRegistrada != null) ? ResponseEntity.ok(alertaRegistrada) : ResponseEntity.badRequest().build();
    }
}
