package com.proyecto.db.dtos;

import java.sql.Timestamp;
import java.util.List;

public class CrearTareaDTO {

    private String descripcion;
    private String estado;
    private Timestamp fechaAsignacion;
    private Timestamp fechaVencimiento;
    private Integer proyectoId;
    private List<Integer> usuarioIds;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Timestamp fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public List<Integer> getUsuarioIds() {
        return usuarioIds;
    }

    public void setUsuarioIds(List<Integer> usuarioIds) {
        this.usuarioIds = usuarioIds;
    }
}
