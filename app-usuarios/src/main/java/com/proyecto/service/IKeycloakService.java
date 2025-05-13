package com.proyecto.service;

import com.proyecto.db.dto.UsuarioKeycloakDto;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeycloakService {
    public List<UserRepresentation> findAllUsers();
    public UserRepresentation searchUserByEmail(String mail);
    public String createUser(UsuarioKeycloakDto usuarioDTO);
    public void deleteUser(String userId);
    public void updateUser(String userId, UsuarioKeycloakDto usuarioDTO);
}
