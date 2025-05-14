package com.proyecto.service;

import com.proyecto.db.Usuario;
import com.proyecto.db.dto.UsuarioKeycloakDto;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.util.KeycloakProvider;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class KeycloakServiceImpl implements IKeycloakService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeycloakProvider.getRealmResource().users().list();
    }

    @Override
    public UserRepresentation searchUserByEmail(String mail) {
        return KeycloakProvider.getRealmResource().users().searchByEmail(mail, true).getFirst();
    }

    @Override
    public String createUser(@NonNull UsuarioKeycloakDto usuarioDTO) {
        try {
            UsersResource userResource = KeycloakProvider.getUsersResource();

            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(usuarioDTO.getUsername());
            userRepresentation.setFirstName(usuarioDTO.getFirstName());
            userRepresentation.setLastName(usuarioDTO.getLastName());
            userRepresentation.setEmail(usuarioDTO.getEmail());
            userRepresentation.setEmailVerified(true);
            userRepresentation.setEnabled(true);

            Response response = userResource.create(userRepresentation);

            if (response.getStatus() != 201) {
                return "Error al crear usuario en Keycloak: " + response.getStatus();
            }

            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(true);
            passwordCred.setType("password");
            passwordCred.setValue(usuarioDTO.getPassword());

            userResource.get(userId).resetPassword(passwordCred);

            // Asignar Rol al Usuario en Keycloak
            RealmResource realmResource = KeycloakProvider.getRealmResource();
            RoleRepresentation roleRepresentation = realmResource.roles().get(usuarioDTO.getRol()).toRepresentation();
            userResource.get(userId).roles().realmLevel().add(Collections.singletonList(roleRepresentation));

            userResource.get(userId).resetPassword(passwordCred);
            Usuario usuario = new Usuario();
            usuario.setId(null);
            usuario.setNombre(usuarioDTO.getFirstName() + " " + usuarioDTO.getLastName());
            usuario.setCorreo(usuarioDTO.getEmail());
            usuario.setRol(usuarioDTO.getRol());
            usuario.setFechaCreacion(Timestamp.from(Instant.now()));

            this.usuarioRepository.save(usuario);

            return userId;

        } catch (Exception e) {
            return "Error al crear usuario: " + e.getMessage();
        }
    }

    @Override
    public void deleteUser(String userId) {

        UsersResource usersResource = KeycloakProvider.getUsersResource();
        usersResource.get(userId).remove();

    }

    @Override
    public void updateUser(String userId, @NonNull UsuarioKeycloakDto usuarioDTO) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(usuarioDTO.getUsername());
        userRepresentation.setFirstName(usuarioDTO.getFirstName());
        userRepresentation.setLastName(usuarioDTO.getLastName());
        userRepresentation.setEmail(usuarioDTO.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        System.out.println("Modificando Keycloak");

        UsersResource usersResource = KeycloakProvider.getUsersResource();
        usersResource.get(userId).update(userRepresentation);

        RealmResource realmResource = KeycloakProvider.getRealmResource();
        RoleRepresentation newRole = realmResource.roles().get(usuarioDTO.getRol()).toRepresentation();

        // Eliminar el rol anterior si es necesario
        UserResource userResource = usersResource.get(userId);
        List<RoleRepresentation> currentRoles = userResource.roles().realmLevel().listAll();
        for (RoleRepresentation role : currentRoles) {

            if (!role.getName().equals("default-roles-spring-boot-realm")) {
                userResource.roles().realmLevel().remove(Collections.singletonList(role));
                System.out.println("Rol " + role.getName() + " eliminado.");
            }
        }

        // Asignar el rol actualizado
        userResource.roles().realmLevel().add(Collections.singletonList(newRole));
        System.out.println("Rol actualizado: " + usuarioDTO.getRol());

        // Asignar el nuevo rol
        userResource.roles().realmLevel().add(Collections.singletonList(newRole));
    }
}
