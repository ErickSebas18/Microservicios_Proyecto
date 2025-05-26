package com.proyecto.repository;

import com.proyecto.db.Evento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByUsuarioId(Integer usuarioId);
}
