package com.proyecto.repository;

import com.proyecto.db.Usuario;
import com.proyecto.db.dto.UsuarioDTO;
import com.proyecto.projections.UsuarioProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<UsuarioProjection> findByCorreo(String correo);

    List<UsuarioProjection> findByRol(String rol);

    List<UsuarioProjection> findAllProjectedBy();

    UsuarioProjection findUsuarioDTOById(@Param("id") Integer id);

    @Query("SELECT u.rol, COUNT(u) FROM Usuario u GROUP BY u.rol")
    List<Object[]> contarUsuariosPorRol();

    @Query(value = """
    SELECT DATE(fecha_creacion) AS dia, EXTRACT(HOUR FROM fecha_creacion) AS hora, COUNT(*)
    FROM usuarios
    GROUP BY dia, hora
    ORDER BY dia, hora
    """, nativeQuery = true)
    List<Object[]> countUsuariosPorHora();

}
