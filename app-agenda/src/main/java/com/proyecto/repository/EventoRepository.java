package com.proyecto.repository;

import com.proyecto.db.Evento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByUsuarioId(Integer usuarioId);

    @Query("SELECT e FROM Evento e WHERE CURRENT_TIMESTAMP BETWEEN e.fechaInicio AND e.fechaFin")
    List<Evento> eventosActivos();

    @Query(value = """
    SELECT AVG(EXTRACT(EPOCH FROM (fecha_fin - fecha_inicio))) / 3600.0
    FROM eventos
    """, nativeQuery = true)
    Double promedioDuracionHoras();

}
