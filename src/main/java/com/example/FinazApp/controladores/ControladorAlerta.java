package com.example.FinazApp.controladores;


import com.example.FinazApp.DTOs.AlertaDTO;
import com.example.FinazApp.servicios.ServicioAlerta;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    @GetMapping("/ObtenerAlertaPorUser/{id_usuario}")
    public ResponseEntity<List<AlertaDTO>> listarAlertaPorUser(@PathVariable Long id_usuario) {
        List<AlertaDTO> alertas = servicioAlerta.ObtenerAlerta(id_usuario);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/ObtenerAlertaPorAnio/{id_usuario}")
    public ResponseEntity<List<AlertaDTO>> listarAlertaPorAnio(@PathVariable Long id_usuario) {
        List<AlertaDTO> alertas = servicioAlerta.ObtenerAlertaFecha(id_usuario);
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/ObtenerAlertaPorMes/{id_usuario}")
    public ResponseEntity<List<AlertaDTO>> listarAlertaPorMes(@PathVariable Long id_usuario) {
        List<AlertaDTO> alertas = servicioAlerta.ObtenerAlertaEsteMes(id_usuario);
        return ResponseEntity.ok(alertas);
    }

}
