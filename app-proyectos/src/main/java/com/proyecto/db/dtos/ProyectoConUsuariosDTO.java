package com.proyecto.db.dtos;

import com.proyecto.db.Proyecto;
import com.proyecto.db.Tarea;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProyectoConUsuariosDTO {

    private Integer id;
    private String titulo;
    private String descripcion;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private String estado;
    private List<UsuarioDTO> usuarios;
//    private List<TareaDTO> tareas;

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

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

//    public List<TareaDTO> getTareas() {
//        return tareas;
//    }
//
//    public void setTareas(List<TareaDTO> tareas) {
//        this.tareas = tareas;
//    }

    public static ProyectoConUsuariosDTO toProyectoConUsuariosDTO(Proyecto p, List<UsuarioDTO> usuarios){
        ProyectoConUsuariosDTO dto = new ProyectoConUsuariosDTO();
        dto.setId(p.getId());
        dto.setTitulo(p.getTitulo());
        dto.setDescripcion(p.getDescripcion());
        dto.setFechaInicio(p.getFechaInicio());
        dto.setFechaFin(p.getFechaFin());
        dto.setUsuarios(usuarios);
        dto.setEstado(p.getEstado());
//        List<TareaDTO> tareasDTO = p.getTareas().stream().map(TareaDTO::toTareaDTO).collect(Collectors.toList());
//
//        dto.setTareas(tareasDTO);
        return dto;
    }
}
