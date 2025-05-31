package com.proyecto.repository;

import com.proyecto.db.EmailRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRegistroRepository extends JpaRepository<EmailRegistro, Integer> {
    @Query("SELECT e.servicio, COUNT(e) FROM EmailRegistro e GROUP BY e.servicio")
    List<Object[]> countGroupedByServicio();

}
