package com.example.FinazApp.servicios;

import com.example.FinazApp.DTOs.AlertaDTO;
import com.example.FinazApp.entidades.Alerta;
import com.example.FinazApp.entidades.Usuario;
import com.example.FinazApp.repositorios.RepositorioAlerta;
import com.example.FinazApp.repositorios.RepositorioGasto;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class ServicioAlerta {

    //Inyección de dependencias
    private final RepositorioAlerta repositorioAlerta; //
    private ModelMapper modelMapper; // Utilizado para mapear entre entidades y DTOs
    private final RepositorioUsuario repositorioUsuario; // Repositorio para operaciones con usuario


    public AlertaDTO RegistrarAlerta(AlertaDTO alertaDTO, Long usuarioId) {

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alerta nuevaAlerta = modelMapper.map(alertaDTO, Alerta.class);
        nuevaAlerta.setUsuario(usuario);

        Alerta AlertaGuardada = repositorioAlerta.save(nuevaAlerta);

        return modelMapper.map(AlertaGuardada, AlertaDTO.class);

    }

}