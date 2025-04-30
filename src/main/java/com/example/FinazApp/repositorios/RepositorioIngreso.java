package com.example.FinazApp.repositorios;

import com.example.FinazApp.entidades.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioIngreso  extends JpaRepository<Ingreso, Long>, JpaSpecificationExecutor<Ingreso> {

    @Query("SELECT i FROM Ingreso i " +
            "WHERE i.usuario.id_usuario = :usuarioId " +
            "AND i.tipo_ingreso = 'casual' " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)")
    List<Ingreso> verificacion(@Param("usuarioId") Long usuarioId);


    @Query("SELECT i FROM Ingreso i WHERE i.usuario.id_usuario = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND i.tipo_ingreso = 'mensual'")
    List<Ingreso> findIngresosMensualesByUsuarioId(@Param("usuarioId") Long usuarioId);
}
