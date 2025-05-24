package com.proyecto.service;

import com.proyecto.db.ArchivoDocumento;
import com.proyecto.db.Documento;
import com.proyecto.db.dto.DocumentoDto;
import com.proyecto.projections.DocumentoProjection;
import com.proyecto.repository.ArchivoDocumentoRepository;
import com.proyecto.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ArchivoDocumentoRepository archivoDocumentoRepository;

    @Autowired
    private ArchivoDocumentoService archivoDocumentoService;

    public List<DocumentoProjection> getAllDocuments() {
        return this.documentoRepository.findAllProjectedBy();
    }

    public List<Documento> getAllDocumentsByProyecto(Integer id) {
        return this.documentoRepository.findAllByProyectoId(id);
    }

    public Documento getDocumentById(Integer id) {
        return this.documentoRepository.findById(id).orElse(null);
    }

    public Documento saveDocument(DocumentoDto documento) {
        try {
            Documento doc = new Documento();
            doc.setNombre(documento.getNombre());
            doc.setTipo(documento.getTipo());
            doc.setDescripcion(documento.getDescripcion());
            doc.setFechaSubida(Timestamp.from(Instant.now()));
            doc.setUsuarioId(documento.getUsuarioId());
            doc.setProyectoId(documento.getProyectoId());
            doc.setId(null);
            Documento savedDocumento = this.documentoRepository.save(doc);

            ArchivoDocumento archivo =  new ArchivoDocumento();
            archivo.setDocumentoId(savedDocumento.getId());
            archivo.setTamanio(documento.getTamanio());
            archivo.setUrl(documento.getUrl());
            this.archivoDocumentoService.saveFile(archivo);

            return savedDocumento;
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean deleteDocumentById(Integer id) {
        if (!this.documentoRepository.existsById(id)) {
            return false;
        }
        this.documentoRepository.deleteById(id);
        this.comentarioService.eliminarComentarioPorDocumento(id);
        return true;
    }

    public String updateDocument(Integer id, Documento documentoActualizado) {
        try {
            if (documentoActualizado != null) {
                var documento = documentoRepository.findById(id).get();
                documento.setNombre(documentoActualizado.getNombre());
                documento.setFechaSubida(documentoActualizado.getFechaSubida());
                documento.setDescripcion(documentoActualizado.getDescripcion());
                documento.setTipo(documentoActualizado.getTipo());
                this.documentoRepository.save(documento);
                return "Documento actualizado correctamente";
            } else {
                return "Documento no encontrado con el id: " + id;
            }
        } catch (Exception e) {
            return "Error al actualizar el documento: " + e.getMessage();
        }
    }

    public String eliminarDocumentosPorProyecto(Integer proyectoId) {
        try {
            List<Documento> docs = this.documentoRepository.findAllByProyectoId(proyectoId);
            for (Documento doc : docs){
               if (this.documentoRepository.existsById(doc.getId())) {
                   this.archivoDocumentoRepository.deleteByDocumentoId(doc.getId());
                   this.comentarioService.eliminarComentarioPorDocumento(doc.getId());
               }
            }
            this.documentoRepository.deleteByProyectoId(proyectoId);
            return "Documentos eliminados";
        }catch (Exception e){
            return "Error al eliminar documentos: " + e.getMessage();
        }

    }
}
