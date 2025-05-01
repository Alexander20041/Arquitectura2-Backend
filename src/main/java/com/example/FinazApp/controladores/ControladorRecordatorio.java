package com.example.FinazApp.controladores;

import com.example.FinazApp.DTOs.RecordatorioDTO;
import com.example.FinazApp.servicios.ServicioRecordatorio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/Finanzapp/Recordatorios")
public class ControladorRecordatorio {

    //Inyección de dependencias
    private final ServicioRecordatorio servicioRecordatorio;

    @PostMapping("/registro/Recordatorio/{id_usuario}")
    public ResponseEntity<RecordatorioDTO> registrarUsuario(@RequestBody RecordatorioDTO recordatorioDTO, @PathVariable Long id_usuario) {
        RecordatorioDTO recordatorioInsertado = servicioRecordatorio.RegistrarRecordatorio(recordatorioDTO, id_usuario);
        if (recordatorioInsertado != null) {
            return ResponseEntity.ok(recordatorioInsertado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}