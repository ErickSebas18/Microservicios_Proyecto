package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.db.Proyecto;
import com.proyecto.projections.ProyectoProjection;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

}
