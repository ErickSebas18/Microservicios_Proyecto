package com.proyecto.repository;

import com.proyecto.db.Comentario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    List<Comentario> findAllByDocumentoId(Integer documentoId);
    void deleteByDocumentoId(Integer documentoId);
}
