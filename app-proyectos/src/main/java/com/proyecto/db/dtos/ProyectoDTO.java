package com.proyecto.db.dtos;

import com.proyecto.db.Proyecto;

import java.sql.Timestamp;

public class ProyectoDTO {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private String estado;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static ProyectoDTO toProyectoDTO(Proyecto p){
        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(p.getId());
        dto.setTitulo(p.getTitulo());
        dto.setDescripcion(p.getDescripcion());
        dto.setFechaInicio(p.getFechaInicio());
        dto.setFechaFin(p.getFechaFin());
        dto.setEstado(p.getEstado());
        return dto;
    }

}
