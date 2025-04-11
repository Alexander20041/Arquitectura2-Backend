package com.example.FinazApp.repositorios;

import com.example.FinazApp.entidades.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;


public interface RepositorioIngreso  extends JpaRepository<Ingreso, Long>, JpaSpecificationExecutor<Ingreso> {

    @Query("SELECT i FROM Ingreso i WHERE i.usuario.id = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND i.tipoIngreso = 'mensual'")
    List<Ingreso> findIngresosMensualesDelMes(@Param("usuarioId") Long usuarioId);

    @Query("SELECT i FROM Ingreso i WHERE i.usuario.id = :usuarioId " +
            "AND EXTRACT(YEAR FROM i.fecha) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM i.fecha) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND i.tipoIngreso = 'casual'")
    List<Ingreso> findIngresosCasualesDelMes(@Param("usuarioId") Long usuarioId);

    @Override
    Optional<Ingreso> findById(Long id);
}