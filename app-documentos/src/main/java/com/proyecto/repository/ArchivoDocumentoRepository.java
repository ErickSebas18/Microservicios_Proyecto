package com.proyecto.repository;

import com.proyecto.db.ArchivoDocumento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ArchivoDocumentoRepository extends JpaRepository<ArchivoDocumento, Integer> {

    List<ArchivoDocumento> findAll();
    ArchivoDocumento findByDocumentoId(Integer documentoId);
    void deleteByDocumentoId(Integer documentoId);
}