package com.proyecto.controller;

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
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<?> findAllDocuments() {
        try {
            return new ResponseEntity<>(this.documentoService.getAllDocuments(), null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(path = "/todos/{proyectoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Documento> findAllDocumentsByProject(@PathVariable Integer proyectoId) {
        return documentoService.getAllDocumentsByProyecto(proyectoId).stream().map(
                documento -> {
                    Documento dc = new Documento();
                    dc.setId(documento.getId());
                    dc.setNombre(documento.getNombre());
                    dc.setDescripcion(documento.getDescripcion());
                    dc.setFechaSubida(documento.getFechaSubida());
                    dc.setProyectoId(documento.getProyectoId());
                    return dc;
                }
        ).collect(Collectors.toList());
    }

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveDocument(@RequestBody DocumentoDto documento) {
        try {
            Documento doc = new Documento();
            doc.setNombre(documento.getNombre());
            doc.setTipo(documento.getTipo());
            doc.setDescripcion(documento.getDescripcion());
            doc.setFechaSubida(Timestamp.from(Instant.now()));
            doc.setUsuarioId(documento.getUsuarioId());
            doc.setProyectoId(documento.getProyectoId());
            Documento savedDocumento = this.documentoService.saveDocument(doc);

            ArchivoDocumento archivo =  new ArchivoDocumento();
            archivo.setDocumentoId(savedDocumento.getId());
            archivo.setUrl(documento.getUrl());
            this.archivoDocumentoService.saveFile(archivo);

            return new ResponseEntity<>(savedDocumento, null, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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

    @DeleteMapping(path = "/eliminar-del-proyecto/{id}")
    public ResponseEntity<?> deleteDocumentsOnProject(@PathVariable Integer id) {
        try {
            this.documentoService.eliminarDocumentosPorProyecto(id);
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
