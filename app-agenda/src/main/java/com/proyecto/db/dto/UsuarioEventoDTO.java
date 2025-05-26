package com.proyecto.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEventoDTO {
    private Integer id;
    private Integer eventoId;
    private Integer usuarioId;
}
