package com.example.FinazApp.repositorios;

import com.example.FinazApp.entidades.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioRecordatorio  extends JpaRepository<Recordatorio, Long>, JpaSpecificationExecutor<Recordatorio>  {

}