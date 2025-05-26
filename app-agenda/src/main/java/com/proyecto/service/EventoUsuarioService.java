package com.proyecto.service;

import com.proyecto.db.EventoUsuario;
import com.proyecto.db.dto.UsuarioEventoDTO;
import com.proyecto.repository.EventoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoUsuarioService {

    @Autowired
    private EventoUsuarioRepository eventoUsuarioRepository;

    public List<EventoUsuario> getEventosByUsuarioId(Integer usuarioId) {
        return eventoUsuarioRepository.findByUsuarioId(usuarioId);
    }

    public List<EventoUsuario> getUsuariosByEventoId(Integer eventoId) {
        List<EventoUsuario> relaciones = eventoUsuarioRepository.findByEventoId(eventoId);
        return relaciones;
    }

    public EventoUsuario save(EventoUsuario eventoUsuario) {
        return eventoUsuarioRepository.save(eventoUsuario);
    }

    public EventoUsuario update(Integer id, EventoUsuario updatedEventoUsuario) {
        return eventoUsuarioRepository.findById(id)
                .map(eventoUsuario -> {
                    eventoUsuario.setEvento(updatedEventoUsuario.getEvento());
                    eventoUsuario.setUsuarioId(updatedEventoUsuario.getUsuarioId());
                    // Agrega aquí cualquier otro campo que quieras actualizar
                    return eventoUsuarioRepository.save(eventoUsuario);
                })
                .orElseThrow(() -> new RuntimeException("EventoUsuario no encontrado con id " + id));
    }

    public void deleteById(Integer id) {
        eventoUsuarioRepository.deleteById(id);
    }
}
