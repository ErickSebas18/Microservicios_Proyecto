package com.proyecto.service;

import com.proyecto.db.Usuario;
import com.proyecto.db.dto.ConteoUsuariosPorHoraDTO;
import com.proyecto.db.dto.UsuarioDTO;
import com.proyecto.db.dto.UsuarioKeycloakDto;
import com.proyecto.projections.UsuarioProjection;
import com.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioProjection> getAllUsers() {
        return usuarioRepository.findAllProjectedBy();
    }

    public List<UsuarioProjection> getUsersByRole(String rol) {
        return this.usuarioRepository.findByRol(rol);
    }

    public Optional<UsuarioProjection> getUserByCorreo(String correo) {
        return this.usuarioRepository.findByCorreo(correo);
    }

    public UsuarioProjection getById(Integer id) {
        return this.usuarioRepository.findUsuarioDTOById(id);
    }

    public Usuario saveUser(Usuario usuario) {
        usuario.setId(null);
        return this.usuarioRepository.save(usuario);
    }

    public Boolean deleteUserById(Integer id) {
        if (!this.usuarioRepository.existsById(id)) {
            return false;
        }
        this.usuarioRepository.deleteById(id);
        return true;
    }

    public String updateUser(Integer id, UsuarioKeycloakDto usuarioActualizado) {
        try {
            if (usuarioActualizado != null) {
                var user = usuarioRepository.findById(id).get();
                user.setNombre(usuarioActualizado.getFirstName() + " " + usuarioActualizado.getLastName());
                user.setCorreo(usuarioActualizado.getEmail());
                user.setRol(usuarioActualizado.getRol());
                this.usuarioRepository.save(user);
                return "Usuario actualizado correctamente";
            } else {
                return "Usuario no encontrado con el id: " + id;
            }
        } catch (Exception e) {
            return "Error al actualizar el usuario : " + e.getMessage();
        }
    }

    public Map<String, Long> contarUsuariosPorRol() {
        List<Object[]> resultados = this.usuarioRepository.contarUsuariosPorRol();
        Map<String, Long> conteoPorRol = new HashMap<>();

        for (Object[] resultado : resultados) {
            String rol = (String) resultado[0];
            Long cantidad = (Long) resultado[1];
            conteoPorRol.put(rol, cantidad);
        }

        return conteoPorRol;
    }

    public long contarTodosLosUsuarios() {
        return this.usuarioRepository.count();
    }

    public List<ConteoUsuariosPorHoraDTO> contarUsuariosPorHora() {
        List<Object[]> resultados = usuarioRepository.countUsuariosPorHora();
        List<ConteoUsuariosPorHoraDTO> conteos = new ArrayList<>();

        for (Object[] fila : resultados) {
            LocalDate dia = ((java.sql.Date) fila[0]).toLocalDate();
            int hora = ((Number) fila[1]).intValue();
            long cantidad = ((Number) fila[2]).longValue();

            conteos.add(new ConteoUsuariosPorHoraDTO(dia, hora, cantidad));
        }

        return conteos;
    }
}
