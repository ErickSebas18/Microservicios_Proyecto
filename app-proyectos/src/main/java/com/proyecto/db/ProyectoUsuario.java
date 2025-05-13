package com.proyecto.db;

import jakarta.persistence.*;

@Entity
@Table(name = "proyecto_usuarios")
public class ProyectoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyec_usua_id")
    private Integer id;
    private Integer usuarioId;
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
