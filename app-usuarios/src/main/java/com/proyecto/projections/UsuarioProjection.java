package com.proyecto.projections;

import java.sql.Timestamp;

public interface UsuarioProjection {

    Integer getId();

    String getNombre();

    String getCorreo();

    String getRol();
}
