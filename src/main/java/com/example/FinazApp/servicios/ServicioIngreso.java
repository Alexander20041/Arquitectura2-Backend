package com.example.FinazApp.servicios;

import com.example.FinazApp.DTOs.IngresoDTO;
import com.example.FinazApp.entidades.Ingreso;
import com.example.FinazApp.entidades.Usuario;
import com.example.FinazApp.repositorios.RepositorioIngreso;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
public class ServicioIngreso implements Serializable {

    private ModelMapper modelMapper;
    private final RepositorioIngreso repositorioIngreso;
    private final RepositorioUsuario repositorioUsuario;


    public IngresoDTO RegistrarIngreso(IngresoDTO ingresoDTO, Long usuarioId) {
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ingreso nuevoIngreso = modelMapper.map(ingresoDTO, Ingreso.class);
        nuevoIngreso.setUsuario(usuario);

        Ingreso ingresoGuardado = repositorioIngreso.save(nuevoIngreso);

        return modelMapper.map(ingresoGuardado, IngresoDTO.class);
    }

    public List<IngresoDTO> BuscarIngresosCasualesPorAnio(Long id_usuario) {

        List<Ingreso> ingresos = repositorioIngreso.verificacion(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .collect(Collectors.toList());

    }


    public List<IngresoDTO> BuscarIngresosMensuales(Long id_usuario) {

        List<Ingreso> ingresos = repositorioIngreso.findIngresosMensualesByUsuarioId(id_usuario);

        return ingresos.stream()
                .map(ingreso -> modelMapper.map(ingreso, IngresoDTO.class))
                .collect(Collectors.toList());
    }
}
