package com.proyecto.projections;

import java.sql.Timestamp;

public interface TareaProjection {
    Integer getId();
    String getDescripcion();
    String getEstado();
    Timestamp getFechaAsignacion();
    Timestamp getFechaVencimiento();
    Integer getProyectoId();
    String getProyectoTitulo();
}
