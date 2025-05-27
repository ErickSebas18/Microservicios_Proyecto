package com.proyecto.repository;

import com.proyecto.db.EmailRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRegistroRepository extends JpaRepository<EmailRegistro, Integer> {}
