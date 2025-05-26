package com.proyecto.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "email_registro")
@Data
public class EmailRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Integer id;

    private String para;
    private String asunto;
    private String mensaje;
}
