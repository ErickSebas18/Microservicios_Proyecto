package com.proyecto.db.dtos;

import com.proyecto.db.Tarea;

public class TareaLigero {
    private Integer id;
    private String descripcion;
    private String estado;

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

    public static TareaDTO toTareaLigero(Tarea t){
        TareaDTO dto = new TareaDTO();
        dto.setId(t.getId());
        dto.setDescripcion(t.getDescripcion());
        dto.setEstado(t.getEstado());
        return dto;
    }
}
