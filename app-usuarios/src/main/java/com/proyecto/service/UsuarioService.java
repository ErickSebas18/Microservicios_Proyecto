package com.proyecto.service;

import com.proyecto.db.Usuario;
import com.proyecto.db.dto.ConteoUsuariosPorHoraDTO;
import com.proyecto.db.dto.UsuarioKeycloakDto;
import com.proyecto.projections.UsuarioProjection;
import com.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

                if (optionalUsuario.isEmpty()) {
                    return "Usuario no encontrado con el id: " + id;
                }

                Usuario user = optionalUsuario.get();
                user.setNombre(usuarioActualizado.getFirstName() + " " + usuarioActualizado.getLastName());
                user.setCorreo(usuarioActualizado.getEmail());
                user.setRol(usuarioActualizado.getRol());
                user.setTelefono(usuarioActualizado.getTelefono());
                user.setCiudad(usuarioActualizado.getCiudad());
                user.setProvincia(usuarioActualizado.getProvincia());

                // Este campo puedes actualizarlo si estás haciendo un cambio por acceso reciente
                user.setUltimoAcceso(Timestamp.from(Instant.now()));

                // Si lo estás actualizando manualmente, puedes tomarlo del DTO
                user.setActivo(usuarioActualizado.getActivo());

                usuarioRepository.save(user);
                return "Usuario actualizado correctamente";
            } else {
                return "Datos del usuario a actualizar no proporcionados.";
            }
        } catch (Exception e) {
            return "Error al actualizar el usuario: " + e.getMessage();
        }
    }

    public String actualizarActivo(Integer id, Boolean nuevoEstado) {
        try {
            Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

            if (optionalUsuario.isEmpty()) {
                return "Usuario no encontrado con el id: " + id;
            }

            Usuario usuario = optionalUsuario.get();
            usuario.setActivo(nuevoEstado);
            usuarioRepository.save(usuario);

            return "Estado "+nuevoEstado+" actualizado correctamente.";
        } catch (Exception e) {
            return "Error al actualizar el estado 'activo': " + e.getMessage();
        }
    }

    public String actualizarUltimoAcceso(Integer id) {
        try {
            Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
            if (optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();
                usuario.setUltimoAcceso(Timestamp.valueOf(LocalDateTime.now()));
                usuarioRepository.save(usuario);
                return "Último acceso actualizado correctamente.";
            } else {
                return "Usuario no encontrado con ID: " + id;
            }
        } catch (Exception e) {
            return "Error al actualizar el último acceso: " + e.getMessage();
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
