package com.example.FinazApp.servicios;

import com.example.FinazApp.DTOs.GastoDTO;
import com.example.FinazApp.entidades.Gasto;
import com.example.FinazApp.entidades.Usuario;
import com.example.FinazApp.repositorios.RepositorioGasto;
import com.example.FinazApp.repositorios.RepositorioIngreso;
import com.example.FinazApp.repositorios.RepositorioUsuario;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    public GastoDTO ModificarGasto(Long id_gasto, GastoDTO gastoDTO) {
        // Buscar el gasto por su ID en el repositorio
        Optional<Gasto> gastoOptional = repositorioGasto.findById(id_gasto);

        // Validar si el gasto existe
        if (gastoOptional.isPresent()) {
            Gasto gasto = gastoOptional.get();

            // Actualizar los campos del gasto con los datos del DTO
            gasto.setNombre_gasto(gastoDTO.getNombre_gasto());
            gasto.setCategoria(gastoDTO.getCategoria());
            gasto.setValor(gastoDTO.getValor());
            gasto.setFecha(gastoDTO.getFecha());

            // Guardar los cambios en el repositorio
            Gasto gastoActualizado = repositorioGasto.save(gasto);

            // Convertir la entidad actualizada de nuevo en un DTO para retornarlo
            GastoDTO gastoActualizadoDTO = new GastoDTO();
            gastoActualizadoDTO.setId_gasto(gastoActualizado.getId_gasto());
            gastoActualizadoDTO.setNombre_gasto(gastoActualizado.getNombre_gasto());
            gastoActualizadoDTO.setCategoria(gastoActualizado.getCategoria());
            gastoActualizadoDTO.setValor(gastoActualizado.getValor());
            gastoActualizadoDTO.setFecha(gastoActualizado.getFecha());

            return gastoActualizadoDTO;
        } else {
            // Lanza una excepción si el gasto no existe
            throw new RuntimeException("El gasto con ID " + id_gasto + " no existe.");
        }
    }


    public void EliminarGasto (Long id_gasto){
        repositorioGasto.deleteById(id_gasto);
    }

    @Transactional
    public void eliminarTodosLosGastos(String Categoria , Long id_usuario) {
        repositorioGasto.deleteByUsuarioIdAndCategoria(id_usuario , Categoria);
    }

    public GastoDTO OrdenarPorValorAlto(Long id_usuario){

        Gasto gastos  = repositorioGasto.getValorMasAlto(id_usuario);

        return modelMapper.map(gastos, GastoDTO.class);

    }

    public GastoDTO OrdenarPorValorBajo(Long id_usuario){

        Gasto gastos  = repositorioGasto.getValorMasBajo(id_usuario);

        return modelMapper.map(gastos, GastoDTO.class);

    }



}

