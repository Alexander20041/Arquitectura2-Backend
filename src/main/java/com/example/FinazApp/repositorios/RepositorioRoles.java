package com.example.FinazApp.repositorios;

import com.example.FinazApp.entidades.ERole;
import com.example.FinazApp.entidades.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RepositorioRoles extends JpaRepository<Roles, Long>, JpaSpecificationExecutor<Roles> {

    Optional<Roles> findByName(ERole name);

}
