package com.proyecto.repository;

import com.proyecto.db.Tarea;
import com.proyecto.projections.TareaProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    List<Tarea> findByProyectoId(Integer proyectoId);
    @Query("""
  SELECT t.id AS id,
         t.descripcion AS descripcion,
         t.estado AS estado,
         t.fechaAsignacion AS fechaAsignacion,
         t.fechaVencimiento AS fechaVencimiento,
         t.proyecto.id AS proyectoId,
         t.proyecto.titulo AS proyectoTitulo
  FROM Tarea t
  WHERE t.proyecto.id = :proyectoId
""")
    List<TareaProjection> findProjectedByProyectoId(@Param("proyectoId") Integer proyectoId);
}