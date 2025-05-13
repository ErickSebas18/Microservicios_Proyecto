package com.proyecto.db;

import jakarta.persistence.*;

@Entity
@Table(name = "tarea_usuarios")
public class TareaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarea_usua_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "tarea_id")
    private Tarea tarea;
    private Integer usuarioId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
