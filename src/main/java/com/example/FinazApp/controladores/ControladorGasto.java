package com.example.FinazApp.controladores;

import com.example.FinazApp.DTOs.GastoDTO;
import com.example.FinazApp.servicios.ServicioGasto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/Finanzapp/Gastos")
public class ControladorGasto {

    //Inyección de dependencias
    private final ServicioGasto servicioGasto;

    @PostMapping("/RegistrarGasto/{id_usuario}")
    public ResponseEntity<GastoDTO> registrarGasto(@RequestBody GastoDTO gasto, @PathVariable Long id_usuario) {

        GastoDTO gastoregistrado = servicioGasto.RegistrarGasto(gasto, id_usuario);

        if (gastoregistrado != null) {
            return ResponseEntity.ok(gastoregistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }


    @GetMapping("/ObtenerDineroDisponiblePorFechas/{id_usuario}/{fecha_inicial}/{fecha_final}")
    public ResponseEntity<Double> ObtenerMoneyDispobnible(@PathVariable Long id_usuario, @PathVariable LocalDate fecha_inicial, @PathVariable LocalDate fecha_final) {

        Double Disponible = servicioGasto.ObtenerDisponiblePorFechas(id_usuario , fecha_inicial, fecha_final);

        if (Disponible != null) {
            return ResponseEntity.ok(Disponible);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/GastosMesCategoria/{id_usuario}/{categoria}")
    public ResponseEntity <List<GastoDTO>> obtenerGastosMesCategoria(@PathVariable Long id_usuario, @PathVariable String categoria) {

        List<GastoDTO>  gastos = servicioGasto.BuscarGastosMesCategoria(id_usuario, categoria);
        if (gastos != null) {
            return ResponseEntity.ok(gastos);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerValorGastosMesCategoria/{id_usuario}/{categoria}")
    public ResponseEntity<Double> ObtenerValorGeneral(@PathVariable Long id_usuario , @PathVariable String categoria) {

        Double ValorGeneral = servicioGasto.ObtenerValorGastosMesCategoria(id_usuario, categoria);

        if (ValorGeneral != null) {
            return ResponseEntity.ok(ValorGeneral);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/ObtenerValorGastosMes/{id_usuario}")
    public ResponseEntity<Double> ObtenerValorGeneral(@PathVariable Long id_usuario) {
        Double ValorGeneral = servicioGasto.ValorGastosMes(id_usuario);

        return ResponseEntity.ok(ValorGeneral != null ? ValorGeneral : 0.0);
    }



}