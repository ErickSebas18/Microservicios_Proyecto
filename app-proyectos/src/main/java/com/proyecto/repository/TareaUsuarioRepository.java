package com.proyecto.repository;

import com.proyecto.db.Proyecto;
import com.proyecto.db.Tarea;
import com.proyecto.db.TareaUsuario;
import com.proyecto.projections.TareaProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TareaUsuarioRepository extends JpaRepository<TareaUsuario, Integer> {
    List<TareaUsuario> findByTareaId(Integer tareaId);
    void deleteByTarea(Tarea tarea);
    @Query("""
  SELECT t.id AS id,
         t.descripcion AS descripcion,
         t.estado AS estado,
         t.fechaAsignacion AS fechaAsignacion,
         t.fechaVencimiento AS fechaVencimiento,
         t.proyecto.id AS proyectoId,
         t.proyecto.titulo AS proyectoTitulo
  FROM TareaUsuario tu
  JOIN tu.tarea t
  WHERE tu.usuarioId = :usuarioId
""")
    List<TareaProjection> findTareasByUsuarioId(@Param("usuarioId") Integer usuarioId);
}
