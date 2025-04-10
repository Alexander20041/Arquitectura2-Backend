package com.example.FinazApp.DTOs;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngresoDTO {

    private Long id;
    private String nombreIngreso;
    private Double valor;
    private LocalDate fecha;
    private String tipoIngreso;
    private Long idUsuario;

}
