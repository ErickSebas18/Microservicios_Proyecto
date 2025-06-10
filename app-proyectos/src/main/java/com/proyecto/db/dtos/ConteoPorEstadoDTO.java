package com.proyecto.db.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConteoPorEstadoDTO {
    private String estado;
    private Long cantidad;
}
