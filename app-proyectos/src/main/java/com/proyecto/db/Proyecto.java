package com.proyecto.db;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyecto_id")
    private Integer id;
    private String titulo;
    private String descripcion;
    @Column(name = "fecha_inicio")
    private Timestamp fechaInicio;
    @Column(name = "fecha_fin")
    private Timestamp fechaFin;
    private String estado;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<ProyectoUsuario> proyectoUsuarios;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Tarea> tareas;

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

    public List<ProyectoUsuario> getProyectoUsuarios() {
        return proyectoUsuarios;
    }

    public void setProyectoUsuarios(List<ProyectoUsuario> proyectoUsuarios) {
        this.proyectoUsuarios = proyectoUsuarios;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}