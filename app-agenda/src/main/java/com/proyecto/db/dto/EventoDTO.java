package com.proyecto.db.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class EventoDTO {
    private String titulo;
    private String descripcion;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Integer usuarioId;
    private List<Integer> asignados; // IDs de usuarios a asociar
}
