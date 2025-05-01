package com.example.FinazApp.servicios;

import com.example.FinazApp.DTOs.RecordatorioDTO;
import com.example.FinazApp.entidades.Recordatorio;
import com.example.FinazApp.entidades.Usuario;
import com.example.FinazApp.repositorios.RepositorioRecordatorio;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class ServicioRecordatorio {

    // Inyección de dependencias
    private final RepositorioRecordatorio repositorioRecordatorio;
    private final RepositorioUsuario repositorioUsuario;
    private ModelMapper modelMapper;


    public RecordatorioDTO RegistrarRecordatorio(RecordatorioDTO recordatorioDTO, Long usuarioId) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Recordatorio nuevoRecordatorio = modelMapper.map(recordatorioDTO, Recordatorio.class);
        nuevoRecordatorio.setUsuario(usuario);

        Recordatorio RecordatorioGuardada = repositorioRecordatorio.save(nuevoRecordatorio);

        return modelMapper.map(RecordatorioGuardada, RecordatorioDTO.class);

    }

}
