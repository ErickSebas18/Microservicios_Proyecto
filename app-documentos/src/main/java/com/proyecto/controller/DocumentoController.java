package com.proyecto.controller;

import com.proyecto.clients.UsuarioRestClient;
import com.proyecto.db.ArchivoDocumento;
import com.proyecto.db.Documento;
import com.proyecto.db.dto.DocumentoDto;
import com.proyecto.service.ArchivoDocumentoService;
import com.proyecto.service.ComentarioService;
import com.proyecto.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/documentos")
@EnableMethodSecurity
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ArchivoDocumentoService archivoDocumentoService;

    @Autowired
    private UsuarioRestClient usuarioRestClient;

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping
    public ResponseEntity<?> findAllDocuments() {
        try {
            return new ResponseEntity<>(this.documentoService.getAllDocuments(), null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping(path = "/file/{docId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findFile(@PathVariable Integer docId) {
        try {
            ArchivoDocumento archivo = this.archivoDocumentoService.findByDocumentoId(docId);
            if (archivo == null || archivo.getUrl() == null) {
                return ResponseEntity.notFound().build();
            }

            // Retornamos solo la data base64 y tipo MIME en un objeto JSON
            Map<String, Object> response = new HashMap<>();
            response.put("url", archivo.getUrl());
            response.put("id", archivo.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping(path = "/todos/{proyectoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Documento> findAllDocumentsByProject(@PathVariable Integer proyectoId) {
        return documentoService.getAllDocumentsByProyecto(proyectoId).stream().map(
                doc -> {    var usuario = usuarioRestClient.findById(doc.getUsuarioId());
                    DocumentoDto dto = new DocumentoDto();
                    dto.setTipo(doc.getTipo());
                    dto.setNombre(doc.getNombre());
                    dto.setDescripcion(doc.getDescripcion());
                    dto.setFechaSubida(doc.getFechaSubida());
                    dto.setProyectoId(doc.getProyectoId());
                    dto.setUsuarioNombre(usuario.getNombre());
                    dto.setId(doc.getId());
                    return doc;
                }
        ).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findDocumentById(@PathVariable Integer id) {

       try{
           var documento = this.documentoService.getDocumentById(id);
           if (documento != null) {
               return new ResponseEntity<>(documento, null, HttpStatus.OK);
           }
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
       }
        return null;
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveDocument(@RequestBody DocumentoDto documento) {
        var res = this.documentoService.saveDocument(documento);
        if (res == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }else {
            return new ResponseEntity<>(res, null, HttpStatus.CREATED);
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Integer id) {

       try {
           if (this.documentoService.deleteDocumentById(id)){
               this.archivoDocumentoService.eliminarArchivoPorDocumento(id);
               return ResponseEntity.ok("Documento eliminado");
           } else {
               return ResponseEntity.badRequest().build();
           }
       }
       catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @DeleteMapping(path = "/eliminar-del-proyecto/{id}")
    public ResponseEntity<?> deleteDocumentsOnProject(@PathVariable Integer id) {
        try {
            this.documentoService.eliminarDocumentosPorProyecto(id);
            return ResponseEntity.ok("Documentos eliminados");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable Integer id, @RequestBody Documento documentoActualizado) {
        try {
            return new ResponseEntity<>(this.documentoService.updateDocument(id, documentoActualizado), null,
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/contar-todos")
    public ResponseEntity<Long> contarTodosLosArchivos() {
        long total = archivoDocumentoService.contarTodosLosArchivos();
        return ResponseEntity.ok(total);
    }
}
