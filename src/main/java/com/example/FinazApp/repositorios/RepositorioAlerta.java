package com.example.FinazApp.repositorios;

import com.example.FinazApp.entidades.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioAlerta extends JpaRepository<Alerta, Long>, JpaSpecificationExecutor<Alerta> {


}
