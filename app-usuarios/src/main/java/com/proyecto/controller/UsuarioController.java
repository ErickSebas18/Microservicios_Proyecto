package com.proyecto.controller;

import com.proyecto.db.Usuario;
import com.proyecto.db.dto.UsuarioDTO;
import com.proyecto.db.dto.UsuarioKeycloakDto;
import com.proyecto.projections.UsuarioProjection;
import com.proyecto.service.IKeycloakService;
import com.proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@EnableMethodSecurity
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IKeycloakService iKeycloakService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UsuarioProjection>> findUsers(@RequestParam(required = false) String rol) {
        try {
            List<UsuarioProjection> usuarios = (rol != null)
                    ? usuarioService.getUsersByRole(rol)
                    : usuarioService.getAllUsers();

            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('admin_client')")
    @GetMapping(path = "/keycloak")
    public ResponseEntity<List<?>> findAllUsersKeycloak() {
        try {
            var lista = this.iKeycloakService.findAllUsers();
            return new ResponseEntity<>(lista, null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioProjection> findUserById(@PathVariable Integer id) {
        var usuario = usuarioService.getById(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, null, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping(path = "/keycloak/by-mail/{mail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserByEmail(@PathVariable String mail) {
        var usuario = iKeycloakService.searchUserByEmail(mail);
        System.out.println(usuario.toString());
        if (usuario != null) {
            return new ResponseEntity<>(usuario, null, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping(path = "/db/by-mail/{mail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUserDBByEmail(@PathVariable String mail) {
        var usuario = usuarioService.getUserByCorreo(mail);
        System.out.println(usuario.toString());
        if (usuario != null) {
            return new ResponseEntity<>(usuario, null, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PreAuthorize("hasRole('admin_client')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertarUsuario(@RequestBody UsuarioKeycloakDto usuarioDTO) {
        try {
            return new ResponseEntity<>(iKeycloakService.createUser(usuarioDTO), null, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasRole('admin_client')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            var mail = usuarioService.getById(id).getCorreo();
            var userName = iKeycloakService.searchUserByEmail(mail);
            iKeycloakService.deleteUser(userName.getId());
            return new ResponseEntity<>(usuarioService.deleteUserById(id), null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UsuarioKeycloakDto usuarioActualizado) {
        try {
            var mail = usuarioService.getById(id).getCorreo();
            var user = iKeycloakService.searchUserByEmail(mail);

            // Esto lanza excepción si falla
            iKeycloakService.updateUser(user.getId(), usuarioActualizado);

            // Solo se ejecuta si no hubo error en Keycloak
            var updatedUser = usuarioService.updateUser(id, usuarioActualizado);
            return ResponseEntity.ok(updatedUser);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el usuario.");
        }
    }

}
