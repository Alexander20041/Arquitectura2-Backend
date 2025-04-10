package com.example.FinazApp.entidades;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table (name = "ingresos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingreso implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreIngreso;
    @Column(nullable = false)
    private Double valor;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private String tipoIngreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}