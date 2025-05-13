package com.proyecto.repository;

import com.proyecto.db.Usuario;
import com.proyecto.projections.UsuarioProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<UsuarioProjection> findByCorreo(String correo);

    List<UsuarioProjection> findByRol(String rol);

    List<UsuarioProjection> findAllProjectedBy();
}
