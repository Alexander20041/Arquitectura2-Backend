package com.example.FinazApp.controladores;

import com.example.FinazApp.DTOs.RecordatorioDTO;
import com.example.FinazApp.servicios.ServicioRecordatorio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/ObtenerRecordatorios/{id_usuario}")
    public ResponseEntity<List<RecordatorioDTO>> ListarAlertaPorMes(@PathVariable Long id_usuario) {
        List<RecordatorioDTO> recordatorioConsultado = servicioRecordatorio.ListarRecordatorios(id_usuario);
        if (!recordatorioConsultado.isEmpty()) {
            return ResponseEntity.ok(recordatorioConsultado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/BuscarPorNombre/{nombre}")
    public ResponseEntity<List<RecordatorioDTO>> BuscarPorNombre(@PathVariable String nombre) {
        List<RecordatorioDTO> recordatorioDTO = servicioRecordatorio.BuscarPorNombre(nombre);
        if (recordatorioDTO != null) {
            return ResponseEntity.ok(recordatorioDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/modificar/Recordatorio/{id_recordatorio}")
    public ResponseEntity<RecordatorioDTO> modificarRecordatorio(
            @PathVariable Long id_recordatorio,
            @RequestBody RecordatorioDTO recordatorioDTO) {
        try {
            RecordatorioDTO recordatorioActualizado = servicioRecordatorio.ModificarRecordatorio(id_recordatorio, recordatorioDTO);
            return ResponseEntity.ok(recordatorioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/EliminarRecordatorios/{id_recordatorio}")
    public ResponseEntity<Void> eliminarRecordatorio(@PathVariable("id_recordatorio") Long id_recordatorio) {
        servicioRecordatorio.EliminarRecordatorio(id_recordatorio);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminartodos/{id_usuario}")
    public ResponseEntity<String> eliminarTodos(@PathVariable Long id_usuario) {
        servicioRecordatorio.eliminarTodosLosRecordatorios(id_usuario);
        return ResponseEntity.ok("Todos los recordatorios han sido eliminados.");
    }

}