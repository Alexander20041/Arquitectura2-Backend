package com.example.FinazApp.controladores;

import com.example.FinazApp.DTOs.IngresoDTO;
import com.example.FinazApp.servicios.ServicioIngreso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Finanzapp/Ingresos")
public class ControladorIngreso {

    @Autowired
    ServicioIngreso servicioIngreso;

    @PostMapping("/registrarIngreso")
    public ResponseEntity<IngresoDTO> registrarUsuario(@RequestBody IngresoDTO ingreso ) {

        IngresoDTO ingresoInsertado = servicioIngreso.registrarIngreso(ingreso);

        if (ingresoInsertado != null) {
            return ResponseEntity.ok(ingresoInsertado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
