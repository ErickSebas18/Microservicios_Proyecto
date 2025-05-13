package com.proyecto.repository;

import com.proyecto.db.Documento;
import com.proyecto.db.dto.DocumentoDto;
import com.proyecto.projections.DocumentoProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    List<DocumentoProjection> findAllProjectedBy();
    List<Documento> findAllByProyectoId(Integer proyectoId);
    void deleteByProyectoId(Integer proyectoId);
}
