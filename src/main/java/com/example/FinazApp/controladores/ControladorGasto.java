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

    @GetMapping("/rango/{id_usuario}/{fecha_inicio}/{fecha_final}/{categoria}")
    public ResponseEntity<List<GastoDTO>> ListarPorFechas(@PathVariable Long id_usuario ,  @PathVariable LocalDate fecha_inicio , @PathVariable LocalDate fecha_final ,@PathVariable String categoria ) {

        List<GastoDTO>  gastos = servicioGasto.obtenerGastosPorRangoDeFechas(id_usuario , fecha_inicio, fecha_final , categoria);

        if (gastos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gastos);

    }

    @PutMapping("/ModificarGastos/{id_gasto}")
    public ResponseEntity<GastoDTO> modificarGasto(@RequestBody GastoDTO gasto, @PathVariable Long id_gasto) {

        GastoDTO gastoregistrado = servicioGasto.ModificarGasto(id_gasto , gasto );

        if (gastoregistrado != null) {
            return ResponseEntity.ok(gastoregistrado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/EliminarTodosLosGastos/{id_usuario}/{categoria}")
    public ResponseEntity<Void> eliminarGastos(@PathVariable("id_usuario") Long idUsuario,
                                               @PathVariable("categoria") String categoria) {
        servicioGasto.eliminarTodosLosGastos(categoria , idUsuario);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/EliminarGastos/{id_gasto}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable("id_gasto") Long id_gasto) {
        servicioGasto.EliminarGasto(id_gasto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ObtenerGastoAlto/{id_usuario}") // TODO: Revisar esta línea más tarde
    public ResponseEntity <GastoDTO> ListarGastoAlto(@PathVariable Long id_usuario) {

        GastoDTO  gastos = servicioGasto.OrdenarPorValorAlto(id_usuario);

        if (gastos == null ) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gastos);

    }

    @GetMapping("/ObtenerGastoBajo/{id_usuario}")
    public ResponseEntity<GastoDTO> ListarGastoBajo(@PathVariable Long id_usuario) {

        GastoDTO  gastos = servicioGasto.OrdenarPorValorBajo(id_usuario);

        if (gastos == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gastos);
    }
}