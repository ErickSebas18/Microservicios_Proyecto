package com.proyecto.repository;

import com.proyecto.db.Evento;
import com.proyecto.db.EventoUsuario;
import com.proyecto.db.dto.UsuarioEventoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoUsuarioRepository extends JpaRepository<EventoUsuario, Integer> {
    List<EventoUsuario> findByUsuarioId(Integer usuarioId);
    List<EventoUsuario> findByEventoId(Integer eventoId);
    void deleteByEventoId(Integer eventoId);
    @Query("""
        SELECT eu.evento FROM EventoUsuario eu
        WHERE eu.usuarioId = :usuarioId
    """)
    List<Evento> findEventosByUsuarioAsignado(@Param("usuarioId") Integer usuarioId);
}