package com.proyecto.db.dtos;

import java.sql.Timestamp;
import java.util.List;

public class CrearProyectoDTO {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private String estado;
    private List<Integer> usuarioIds;

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

    public List<Integer> getUsuarioIds() {
        return usuarioIds;
    }

    public void setUsuarioIds(List<Integer> usuarioIds) {
        this.usuarioIds = usuarioIds;
    }
}
