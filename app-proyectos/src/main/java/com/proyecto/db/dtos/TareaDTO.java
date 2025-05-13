package com.proyecto.db.dtos;

import com.proyecto.db.Tarea;

import java.sql.Timestamp;

public class TareaDTO {

    private Integer id;
    private String descripcion;
    private String estado;
    private Timestamp fechaAsignacion;
    private Timestamp fechaVencimiento;
    private Integer proyectoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public static TareaDTO toTareaDTO(Tarea t){
        TareaDTO dto = new TareaDTO();
        dto.setId(t.getId());
        dto.setDescripcion(t.getDescripcion());
        dto.setEstado(t.getEstado());
        dto.setFechaAsignacion(t.getFechaAsignacion());
        dto.setFechaVencimiento(t.getFechaVencimiento());
        dto.setProyectoId(t.getProyecto().getId());
        return dto;
    }
}
