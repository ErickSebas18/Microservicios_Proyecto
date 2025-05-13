package com.proyecto.service;

import com.proyecto.db.Documento;
import com.proyecto.db.dto.DocumentoDto;
import com.proyecto.projections.DocumentoProjection;
import com.proyecto.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    public List<DocumentoProjection> getAllDocuments() {
        return this.documentoRepository.findAllProjectedBy();
    }

    public List<Documento> getAllDocumentsByProyecto(Integer id) {
        return this.documentoRepository.findAllByProyectoId(id);
    }

    public Documento getDocumentById(Integer id) {
        return this.documentoRepository.findById(id).orElse(null);
    }

    public Documento saveDocument(Documento documento) {
        documento.setId(null);
        return this.documentoRepository.save(documento);
    }

    public Boolean deleteDocumentById(Integer id) {
        if (!this.documentoRepository.existsById(id)) {
            return false;
        }
        this.documentoRepository.deleteById(id);
        return true;
    }

    public String updateDocument(Integer id, Documento documentoActualizado) {
        try {
            if (documentoActualizado != null) {
                var documento = documentoRepository.findById(id).get();
                documento.setNombre(documentoActualizado.getNombre());
                documento.setRuta(documentoActualizado.getRuta());
                documento.setFechaSubida(documentoActualizado.getFechaSubida());
                this.documentoRepository.save(documento);
                return "Documento actualizado correctamente";
            } else {
                return "Documento no encontrado con el id: " + id;
            }
        } catch (Exception e) {
            return "Error al actualizar el documento: " + e.getMessage();
        }
    }

    public void eliminarDocumentosPorProyecto(Integer proyectoId) {
        this.documentoRepository.deleteByProyectoId(proyectoId);
    }
}
