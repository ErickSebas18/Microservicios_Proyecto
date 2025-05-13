package com.proyecto.service;

import com.proyecto.db.Usuario;
import com.proyecto.db.dto.UsuarioKeycloakDto;
import com.proyecto.projections.UsuarioProjection;
import com.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Usuario getById(Integer id) {
        return this.usuarioRepository.findById(id).orElse(null);
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
}
