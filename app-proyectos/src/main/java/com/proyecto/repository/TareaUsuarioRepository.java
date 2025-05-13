package com.proyecto.repository;

import com.proyecto.db.Tarea;
import com.proyecto.db.TareaUsuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TareaUsuarioRepository extends JpaRepository<TareaUsuario, Integer> {
    List<TareaUsuario> findByTareaId(Integer tareaId);
    void deleteByTarea(Tarea tarea);
}
