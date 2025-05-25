package com.proyecto.db;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Cleanup;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "tarea_id")
    private Integer id;
    private String descripcion;
    private String estado;
    @Column(name = "fecha_asignacion")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp fechaAsignacion;
    @Column(name = "fecha_vencimiento")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp fechaVencimiento;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL)
    private List<TareaUsuario> tareaUsuarios;

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

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public List<TareaUsuario> getTareaUsuarios() {
        return tareaUsuarios;
    }

    public void setTareaUsuarios(List<TareaUsuario> tareaUsuarios) {
        this.tareaUsuarios = tareaUsuarios;
    }
}
