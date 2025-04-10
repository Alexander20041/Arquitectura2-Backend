package com.example.FinazApp.servicios;

import com.example.FinazApp.DTOs.IngresoDTO;
import com.example.FinazApp.entidades.Ingreso;
import com.example.FinazApp.entidades.Usuario;
import com.example.FinazApp.repositorios.RepositorioIngreso;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioIngreso {

    @Autowired
    RepositorioIngreso repositorioIngreso;
    @Autowired
    RepositorioUsuario repositorioUsuario;

    public IngresoDTO registrarIngreso(IngresoDTO ingresoDTO) {

        // Obtener el usuario desde el idUsuario dentro del DTO
        Usuario usuario = repositorioUsuario.findById(ingresoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Construir el ingreso con el patrón Builder
        Ingreso nuevoIngreso = Ingreso.builder()
                .valor(ingresoDTO.getValor())
                .fecha(ingresoDTO.getFecha())
                .nombreIngreso(ingresoDTO.getNombreIngreso())
                .tipoIngreso(ingresoDTO.getTipoIngreso())
                .usuario(usuario)
                .build();

        // Guardar en base de datos
        Ingreso ingresoGuardado = repositorioIngreso.save(nuevoIngreso);

        // Construir el DTO de retorno
        IngresoDTO ingresoGuardadoDTO = IngresoDTO.builder()
                .id(ingresoGuardado.getId())
                .valor(ingresoGuardado.getValor())
                .fecha(ingresoGuardado.getFecha())
                .nombreIngreso(ingresoGuardado.getNombreIngreso())
                .tipoIngreso(ingresoGuardado.getTipoIngreso())
                .idUsuario(ingresoGuardado.getUsuario().getId())
                .build();

        return ingresoGuardadoDTO;
    }


    public List<IngresoDTO> buscarIngresosMensualesPorMes(Long idUsuario) {

        List<Ingreso> ingresos = repositorioIngreso.findIngresosMensualesDelMes(idUsuario);

        return ingresos.stream()
                .map(ingreso -> IngresoDTO.builder()
                        .id(ingreso.getId())
                        .valor(ingreso.getValor())
                        .fecha(ingreso.getFecha())
                        .nombreIngreso(ingreso.getNombreIngreso())
                        .tipoIngreso(ingreso.getTipoIngreso())
                        .build())
                .toList();
    }

    public List<IngresoDTO> buscarIngresosCasualesPorMes(Long idUsuario) {

        List<Ingreso> ingresos = repositorioIngreso.findIngresosCasualesDelMes(idUsuario);

        return ingresos.stream()
                .map(ingreso -> IngresoDTO.builder()
                        .id(ingreso.getId())
                        .valor(ingreso.getValor())
                        .fecha(ingreso.getFecha())
                        .nombreIngreso(ingreso.getNombreIngreso())
                        .tipoIngreso(ingreso.getTipoIngreso())
                        .build())
                .toList();
    }

    public IngresoDTO modificarIngreso(Long idIngreso, IngresoDTO ingresoDTO) {

        // Paso 1: Buscar el ingreso
        Ingreso ingreso = repositorioIngreso.findById(idIngreso)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));

        // Paso 2: Actualizar campos
        ingreso.setValor(ingresoDTO.getValor());
        ingreso.setFecha(ingresoDTO.getFecha());
        ingreso.setNombreIngreso(ingresoDTO.getNombreIngreso());
        ingreso.setTipoIngreso(ingresoDTO.getTipoIngreso());

        // Paso 3: Guardar
        Ingreso ingresoActualizado = repositorioIngreso.save(ingreso);

        // Paso 4: Construir el DTO actualizado
        return IngresoDTO.builder()
                .id(ingresoActualizado.getId())
                .valor(ingresoActualizado.getValor())
                .fecha(ingresoActualizado.getFecha())
                .nombreIngreso(ingresoActualizado.getNombreIngreso())
                .tipoIngreso(ingresoActualizado.getTipoIngreso())
                .idUsuario(ingresoActualizado.getUsuario().getId())
                .build();
    }

    public void eliminarIngreso(Long idIngreso) {

        repositorioIngreso.deleteById(idIngreso);

    }

}
