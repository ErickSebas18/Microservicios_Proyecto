package com.proyecto.controller;

import com.proyecto.clients.UsuarioRestClient;
import com.proyecto.db.Comentario;
import com.proyecto.db.dto.ComentarioDto;
import com.proyecto.repository.ComentarioRepository;
import com.proyecto.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comentarios")
@EnableMethodSecurity
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRestClient usuarioRestClient;

    /*@GetMapping
    public List<ComentarioDto> findAllComments(){
        return comentarioService.getAllComments().stream().map(
                comentario -> {
                    var usuario = usuarioRestClient.findById(comentario.getUsuarioId());
                    var documento = documentoRestClient.findById(comentario.getDocumentoId());
                    ComentarioDto dto = new ComentarioDto();
                    dto.setId(comentario.getId());
                    dto.setComentario(comentario.getComentario());
                    dto.setFechaCreacion(comentario.getFechaCreacion());
                    dto.setUsuarioNombre(usuario.getNombre());
                    dto.setDocumentoNombre(documento.getNombre());
                    return dto;
                }
        ).collect(Collectors.toList());
    }*/

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping(path = "/todos/{documentoId}")
    public List<ComentarioDto> findAllComments(@PathVariable Integer documentoId){
        return comentarioService.findAllCommentsByDocument(documentoId).stream().map(
                comentario -> {
                    var usuario = usuarioRestClient.findById(comentario.getUsuarioId());
                    ComentarioDto dto = new ComentarioDto();
                    dto.setId(comentario.getId());
                    dto.setComentario(comentario.getComentario());
                    dto.setFechaCreacion(comentario.getFechaCreacion());
                    dto.setUsuarioNombre(usuario.getNombre());
                    return dto;
                }
        ).collect(Collectors.toList());
    }

    /*@GetMapping
    public ResponseEntity<?> findAllComments() {
        try {
            return new ResponseEntity<>(this.comentarioService.getAllComments(), null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
     @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<?> findCommentById(@PathVariable Integer id) {
        var comentario = this.comentarioService.getCommentById(id);
        if (comentario != null) {
            return new ResponseEntity<>(comentario, null, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
     }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveComment(@RequestBody Comentario comentario) {
        try {
            Comentario comentarioSaved = this.comentarioService.saveComment(comentario);
            return new ResponseEntity<>(comentarioSaved, null, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        if (this.comentarioService.deleteCommentById(id)) {
            return ResponseEntity.ok("Comentario eliminado");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> eliminarComentariosPorDocumento(@PathVariable Integer id) {
        try {
            this.comentarioService.eliminarComentarioPorDocumento(id);
            return ResponseEntity.ok("Comentarios eliminados");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Integer id, @RequestBody Comentario comentarioActualizado) {
        try {
            return new ResponseEntity<>(this.comentarioService.updateDocument(id, comentarioActualizado), null,
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
