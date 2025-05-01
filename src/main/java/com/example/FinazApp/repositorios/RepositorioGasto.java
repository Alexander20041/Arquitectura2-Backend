package com.example.FinazApp.repositorios;

import com.example.FinazApp.entidades.Gasto;
import com.example.FinazApp.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositorioGasto extends JpaRepository<Gasto, Long>, JpaSpecificationExecutor<Gasto> {



    @Query("SELECT " +
            "COALESCE(SUM(i.valor), 0) - COALESCE((SELECT SUM(g.valor) FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId AND g.fecha BETWEEN :fechaInf AND :fechaSup), 0) " +
            "FROM Ingreso i WHERE i.usuario.id_usuario = :usuarioId AND i.fecha BETWEEN :fechaInf AND :fechaSup")
    Double getDisponiblePorFechas(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaInf") LocalDate fechaInf,
            @Param("fechaSup") LocalDate fechaSup
    );

    @Query("SELECT g FROM Gasto g " +
            "WHERE g.usuario.id_usuario = :usuarioId " +
            "AND g.categoria = :categoria " +
            "AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    List<Gasto> getGastosMesCategoria(
            @Param("usuarioId") Long usuarioId,
            @Param("categoria") String categoria
    );

    @Query("SELECT g FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId")
    List<Gasto> findGastosByUsuarioId(@Param("usuarioId") Long usuarioId);


    List<Gasto> findByUsuarioAndFechaBetweenAndCategoria(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin , String categoria);


    @Query("SELECT SUM(g.valor) " +
            "FROM Gasto g " +
            "WHERE g.usuario.id_usuario = :usuarioId " +
            "AND g.categoria = :categoria " +
            "AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    Double getValorGastosMesCategoria(@Param("usuarioId") Long usuarioId,
                                      @Param("categoria") String categoria);


    @Query("SELECT SUM(g.valor) FROM Gasto g WHERE g.usuario.id_usuario = :usuarioId AND EXTRACT(YEAR FROM g.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM g.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)")
    Double getValorGastosMes(@Param("usuarioId") Long usuarioId);

}

