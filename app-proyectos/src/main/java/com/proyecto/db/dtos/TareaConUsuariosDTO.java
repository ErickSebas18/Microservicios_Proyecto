package com.proyecto.db.dtos;

import com.proyecto.db.Tarea;

import java.sql.Timestamp;
import java.util.List;

import static com.proyecto.db.dtos.ProyectoDTO.toProyectoDTO;

public class TareaConUsuariosDTO {

    private Integer id;
    private String descripcion;
    private String estado;
    private Timestamp fechaAsignacion;
    private Timestamp fechaVencimiento;
    private ProyectoDTO proyecto;
    private List<UsuarioDTO> usuarios;

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

    public ProyectoDTO getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDTO proyecto) {
        this.proyecto = proyecto;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public static TareaConUsuariosDTO toTareaConUsuariosDTO(Tarea t, List<UsuarioDTO> usuarios){
        TareaConUsuariosDTO dto = new TareaConUsuariosDTO();
        dto.setId(t.getId());
        dto.setDescripcion(t.getDescripcion());
        dto.setEstado(t.getEstado());
        dto.setFechaAsignacion(t.getFechaAsignacion());
        dto.setFechaVencimiento(t.getFechaVencimiento());
        dto.setProyecto(toProyectoDTO(t.getProyecto()));
        dto.setUsuarios(usuarios);
        return dto;
    }
}
