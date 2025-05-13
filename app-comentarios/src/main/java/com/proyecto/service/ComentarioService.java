package com.proyecto.service;

import com.proyecto.db.Comentario;
import com.proyecto.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;


    public List<Comentario> findAllCommentsByDocument(Integer documentoId){
        return comentarioRepository.findAllByDocumentoId(documentoId);
    }

    public List<Comentario> getAllComments(){
        return this.comentarioRepository.findAll();
    }

    public Comentario getCommentById(Integer id) {
        return this.comentarioRepository.findById(id).orElse(null);
    }

    public Comentario saveComment(Comentario comentario) {
        comentario.setId(null);
        return this.comentarioRepository.save(comentario);
    }

    public Boolean deleteCommentById(Integer id) {
        if (!this.comentarioRepository.existsById(id)) {
            return false;
        }
        this.comentarioRepository.deleteById(id);
        return true;
    }

    public String updateDocument(Integer id, Comentario comentarioActualizado) {
        try {
            if (comentarioActualizado != null) {
                var comentario = comentarioRepository.findById(id).get();
                comentario.setFechaCreacion(comentarioActualizado.getFechaCreacion());
                comentario.setComentario(comentarioActualizado.getComentario());
                this.comentarioRepository.save(comentario);
                return "Comentario actualizado correctamente";
            } else {
                return "Comentario no encontrado con el id: " + id;
            }
        } catch (Exception e) {
            return "Error al actualizar el comentario: " + e.getMessage();
        }
    }

    public void eliminarComentarioPorProyecto(Integer proyectoId) {
        this.comentarioRepository.deleteByProyectoId(proyectoId);
    }
}
