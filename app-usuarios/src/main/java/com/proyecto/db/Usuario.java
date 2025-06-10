package com.proyecto.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String correo;

    private String rol;

    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;

    private String telefono;

    private String ciudad;

    private String provincia;

    @Column(name = "ultimo_acceso")
    private Timestamp ultimoAcceso;

    private Boolean activo;
    //feign.client.usuario-service.url
}
