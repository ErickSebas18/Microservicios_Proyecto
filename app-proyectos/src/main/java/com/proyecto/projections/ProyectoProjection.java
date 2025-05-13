package com.proyecto.projections;

import java.sql.Timestamp;

public interface ProyectoProjection {

    Integer getId();

    String getTitulo();

    String getDescripcion();

    Timestamp getFechaInicio();

    Timestamp getFechaFin();

    String getEstado();

    Integer getResponsable();

}
