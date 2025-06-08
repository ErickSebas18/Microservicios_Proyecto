package com.proyecto.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioKeycloakDto {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String rol;
    private String telefono;
    private String ciudad;
    private String provincia;
    private Boolean activo;
}
