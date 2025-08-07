package com.proyecto.service;

import com.proyecto.db.dto.UsuarioKeycloakDto;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeycloakService {
    List<UserRepresentation> findAllUsers();
    UserRepresentation searchUserByEmail(String mail);
    String createUser(UsuarioKeycloakDto usuarioDTO);
    void deleteUser(String userId);
    void updateUser(String userId, UsuarioKeycloakDto usuarioDTO);
}
