package com.proyecto.repository;

import com.proyecto.db.Tarea;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    List<Tarea> findByProyectoId(Integer proyectoId);
}
