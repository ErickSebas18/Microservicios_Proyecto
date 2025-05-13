package com.proyecto.controller;

import com.proyecto.clients.ComentarioRestClient;
import com.proyecto.db.Documento;
import com.proyecto.db.dto.DocumentoDto;
import com.proyecto.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/documentos")
@EnableMethodSecurity
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private ComentarioRestClient comentarioRestClient;

    @GetMapping
    public ResponseEntity<?> findAllDocuments() {
        try {
            return new ResponseEntity<>(this.documentoService.getAllDocuments(), null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(path = "/todos/{proyectoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentoDto> findAllDocumentsByProject(@PathVariable Integer proyectoId) {
        return documentoService.getAllDocumentsByProyecto(proyectoId).stream().map(
                documento -> {
                    DocumentoDto dto = new DocumentoDto();
                    dto.setId(documento.getId());
                    dto.setNombre(documento.getNombre());
                    dto.setRuta(documento.getRuta());
                    dto.setFechaSubida(documento.getFechaSubida());
                    dto.setProyectoId(documento.getProyectoId());
                    return dto;
                }
        ).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findDocumentById(@PathVariable Integer id) {
        var documento = this.documentoService.getDocumentById(id);
        if (documento != null) {
            return new ResponseEntity<>(documento, null, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveDocument(@RequestBody Documento documento) {
        try {
            Documento savedDocumento = this.documentoService.saveDocument(documento);
            return new ResponseEntity<>(savedDocumento, null, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Integer id) {
        if (this.documentoService.deleteDocumentById(id)) {
            return ResponseEntity.ok("Documento eliminado");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/eliminar-del-proyecto/{id}")
    public ResponseEntity<?> deleteDocumentsOnProject(@PathVariable Integer id) {
        try {
            this.documentoService.eliminarDocumentosPorProyecto(id);
            this.comentarioRestClient.eliminarComentarioPorProyecto(id);
            return ResponseEntity.ok("Documentos eliminados");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable Integer id, @RequestBody Documento documentoActualizado) {
        try {
            return new ResponseEntity<>(this.documentoService.updateDocument(id, documentoActualizado), null,
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
