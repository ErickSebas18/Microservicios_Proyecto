package com.proyecto.db;

import lombok.Data;

@Data
public class EmailRequest {
    private String para;
    private String asunto;
    private String mensaje;
}
