package com.proyecto.db.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConteoUsuariosPorHoraDTO {
    private LocalDate dia;
    private int hora;
    private long cantidad;
}
