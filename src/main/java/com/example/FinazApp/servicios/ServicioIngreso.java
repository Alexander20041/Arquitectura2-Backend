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


}
