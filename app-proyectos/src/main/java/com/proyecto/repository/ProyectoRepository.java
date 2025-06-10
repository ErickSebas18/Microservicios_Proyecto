package com.proyecto.repository;

import com.proyecto.db.Proyecto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    @Query("SELECT p.estado, COUNT(p) FROM Proyecto p GROUP BY p.estado")
    List<Object[]> contarProyectosPorEstado();

    @Query(value = """
    SELECT EXTRACT(MONTH FROM fecha_inicio) AS mes,
           EXTRACT(YEAR FROM fecha_inicio) AS anio,
           COUNT(*) AS cantidad
    FROM proyectos
    GROUP BY anio, mes
    ORDER BY anio, mes
    """, nativeQuery = true)
    List<Object[]> countProyectosPorMes();
}
