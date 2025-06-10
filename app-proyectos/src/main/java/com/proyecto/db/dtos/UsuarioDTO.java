package com.proyecto.db.dtos;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String correo;
    private String rol;
    private String activo;
}
