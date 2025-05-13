package com.proyecto.projections;

import java.sql.Timestamp;

public interface DocumentoProjection {
    Integer getId();

    String getNombre();

    String getRuta();

    Timestamp getFechaSubida();

    Integer getProyectoId();

}