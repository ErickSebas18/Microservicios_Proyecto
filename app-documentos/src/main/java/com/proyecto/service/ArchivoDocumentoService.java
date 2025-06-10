package com.proyecto.service;

import com.proyecto.db.ArchivoDocumento;
import com.proyecto.repository.ArchivoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchivoDocumentoService {

    @Autowired
    private ArchivoDocumentoRepository archivoDocumentoRepository;

    public ArchivoDocumento findByDocumentoId(Integer documentoId){
        return this.archivoDocumentoRepository.findByDocumentoId(documentoId);
    }

    public ArchivoDocumento saveFile(ArchivoDocumento archivoDocumento) {
        archivoDocumento.setId(null);
        return this.archivoDocumentoRepository.save(archivoDocumento);
    }

    public Boolean deleteById(Integer id) {
        if (!this.archivoDocumentoRepository.existsById(id)) {
            return false;
        }
        this.archivoDocumentoRepository.deleteById(id);
        return true;
    }

    public String updateFile(Integer id, ArchivoDocumento archivoActualizado) {
        try {
            if (archivoActualizado != null) {
                var archivo = archivoDocumentoRepository.findById(id).get();
                archivo.setUrl(archivoActualizado.getUrl());
                 this.archivoDocumentoRepository.save(archivo);
                return "Archivo actualizado correctamente";
            } else {
                return "Archivo no encontrado con el id: " + id;
            }
        } catch (Exception e) {
            return "Error al actualizar el Archivo: " + e.getMessage();
        }
    }

    public void eliminarArchivoPorDocumento(Integer documentoId) {
        this.archivoDocumentoRepository.deleteByDocumentoId(documentoId);
    }

    public long contarTodosLosArchivos() {
        return this.archivoDocumentoRepository.count();
    }
}
