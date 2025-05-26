package com.proyecto.service;

import com.proyecto.db.Evento;
import com.proyecto.db.EventoUsuario;
import com.proyecto.db.dto.EventoDTO;
import com.proyecto.repository.EventoRepository;
import com.proyecto.repository.EventoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoUsuarioRepository eventoUsuarioRepository;

    public Evento crearEventoConUsuarios(EventoDTO dto) {
        // Crear el evento
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        evento.setUsuarioId(dto.getUsuarioId());

        Evento eventoGuardado = eventoRepository.save(evento);

        // Asociar usuarios
        for (Integer usuarioId : dto.getAsignados()) {
            EventoUsuario eu = new EventoUsuario();
            eu.setEvento(eventoGuardado);
            eu.setUsuarioId(usuarioId);
            eventoUsuarioRepository.save(eu);
        }


        return eventoGuardado;
    }

    public List<Evento> obtenerTodos() {
        return this.eventoRepository.findAll();
    }

    public Evento obtenerPorId(Integer id) {
        return this.eventoRepository.findById(id).orElse(null);
    }

    public List<Evento> eventosAsignados(Integer usuarioId) {
        return eventoUsuarioRepository.findEventosByUsuarioAsignado(usuarioId);
    }

    public List<Evento> eventosCreadosPorUsuario(Integer usuarioId) {
        return eventoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Evento actualizarEvento(Integer id, EventoDTO dto) {
        Optional<Evento> optEvento = this.eventoRepository.findById(id);
        if (optEvento.isEmpty()) return null;

        Evento evento = optEvento.get();
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        evento.setUsuarioId(dto.getUsuarioId());

        Evento eventoActualizado = this.eventoRepository.save(evento);

        // Eliminar asignaciones anteriores
        this.eventoUsuarioRepository.deleteByEventoId(id);

        // Crear nuevas asignaciones
        if (dto.getAsignados() != null) {
            for (Integer userId : dto.getAsignados()) {
                EventoUsuario eu = new EventoUsuario();
                eu.setEvento(eventoActualizado);
                eu.setUsuarioId(userId);
                this.eventoUsuarioRepository.save(eu);
            }
        }

        return eventoActualizado;
    }

    @Transactional
    public void eliminarEvento(Integer id) {
        // Primero eliminar las asignaciones
        this.eventoUsuarioRepository.deleteByEventoId(id);
        this.eventoRepository.deleteById(id);
    }
}