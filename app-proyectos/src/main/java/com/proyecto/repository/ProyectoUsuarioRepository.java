package com.proyecto.repository;

import com.proyecto.db.Proyecto;
import com.proyecto.db.ProyectoUsuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProyectoUsuarioRepository extends JpaRepository<ProyectoUsuario, Integer> {
    List<ProyectoUsuario> findByProyectoId(Integer proyectoId);
    void deleteByProyecto(Proyecto proyecto);
    @Query("SELECT pu.proyecto FROM ProyectoUsuario pu WHERE pu.usuarioId = :usuarioId")
    List<Proyecto> findProyectosByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT pu.proyecto.id FROM ProyectoUsuario pu WHERE pu.usuarioId = :usuarioId")
    List<Integer> findProyectoIdsByUsuarioId(@Param("usuarioId") Integer usuarioId);
}
