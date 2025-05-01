package com.example.FinazApp.servicios;

import com.example.FinazApp.DTOs.GastoDTO;
import com.example.FinazApp.entidades.Gasto;
import com.example.FinazApp.entidades.Usuario;
import com.example.FinazApp.repositorios.RepositorioGasto;
import com.example.FinazApp.repositorios.RepositorioIngreso;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
public class ServicioGasto {

    //Inyección de dependencias
    private ModelMapper modelMapper;  // Utilizado para mapear entre entidades y DTOs
    private final RepositorioGasto repositorioGasto; // Repositorio para operaciones con ingresos
    private final RepositorioIngreso repositorioIngreso; // Repositorio para verificar la existencia del ingreso
    private final RepositorioUsuario repositorioUsuario; // Repositorio para verificar la existencia del usuario

    public GastoDTO RegistrarGasto(GastoDTO gastoDTO, Long usuarioId) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Gasto nuevoGasto = modelMapper.map(gastoDTO, Gasto.class);
        nuevoGasto.setUsuario(usuario);

        Gasto gastoGuardado = repositorioGasto.save(nuevoGasto);

        return modelMapper.map(gastoGuardado, GastoDTO.class);
    }


    public Double ObtenerDisponiblePorFechas (Long id_usuario , LocalDate fechaInf , LocalDate fechaSup ){

        return repositorioGasto.getDisponiblePorFechas(id_usuario, fechaInf, fechaSup);

    }

    public List<GastoDTO> BuscarGastosMesCategoria(Long id_usuario , String categoria){

        List<Gasto> gastos  = repositorioGasto.getGastosMesCategoria(id_usuario , categoria);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .collect(Collectors.toList());

    }


    public Double ObtenerValorGastosMesCategoria (Long id_usuario , String categoria){

        return repositorioGasto.getValorGastosMesCategoria(id_usuario , categoria);

    }


    public Double ValorGastosMes (Long id_usuario){

        return repositorioGasto.getValorGastosMes(id_usuario);

    }


    public List<GastoDTO> obtenerGastosPorRangoDeFechas(Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin , String categoria) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Gasto> gastos  = repositorioGasto.findByUsuarioAndFechaBetweenAndCategoria(usuario , fechaInicio , fechaFin , categoria);

        return gastos.stream()
                .map(gasto -> modelMapper.map(gasto, GastoDTO.class))
                .toList();

    }


}

