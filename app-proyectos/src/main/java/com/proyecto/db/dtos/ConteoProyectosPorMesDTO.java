package com.proyecto.db.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConteoProyectosPorMesDTO {
    private int anio;
    private int mes;
    private long cantidad;
}