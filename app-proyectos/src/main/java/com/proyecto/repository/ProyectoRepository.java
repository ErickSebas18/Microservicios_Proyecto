package com.proyecto.repository;

import com.proyecto.db.Proyecto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
}
