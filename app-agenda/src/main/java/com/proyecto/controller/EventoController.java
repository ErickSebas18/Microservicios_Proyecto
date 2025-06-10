package com.proyecto.controller;

import com.proyecto.db.Evento;
import com.proyecto.db.EventoUsuario;
import com.proyecto.db.dto.EventoDTO;
import com.proyecto.service.EventoService;
import com.proyecto.service.EventoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoUsuarioService eventoUsuarioService;

    // Crear evento con usuarios asignados
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @PostMapping
    public ResponseEntity<?> crearEvento(@RequestBody EventoDTO eventoDTO) {
        try {
            Evento evento = this.eventoService.crearEventoConUsuarios(eventoDTO);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el evento: " + e.getMessage());
        }
    }

    // Obtener todos los eventos
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Evento> eventos = this.eventoService.obtenerTodos();
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener los eventos");
        }
    }

    // Obtener evento por ID
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            Evento evento = this.eventoService.obtenerPorId(id);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener todos los usuarios asignados a un evento
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/usuarios-asignados/{eventoId}")
    public ResponseEntity<?> obtenerUsuariosPorEvento(@PathVariable Integer eventoId) {
        try {
            List<EventoUsuario> usuarios = this.eventoUsuarioService.getUsuariosByEventoId(eventoId);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los usuarios del evento: " + e.getMessage());
        }
    }

    // Obtener eventos asignados a un usuario
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/asignados/{usuarioId}")
    public ResponseEntity<?> obtenerEventosAsignados(@PathVariable Integer usuarioId) {
        try {
            List<Evento> eventos = this.eventoService.eventosAsignados(usuarioId);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener eventos asignados");
        }
    }

    // Obtener eventos creados por un usuario
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/creados/{usuarioId}")
    public ResponseEntity<?> obtenerEventosCreadosPorUsuario(@PathVariable Integer usuarioId) {
        try {
            List<Evento> eventos = this.eventoService.eventosCreadosPorUsuario(usuarioId);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener eventos creados");
        }
    }

    // Actualizar evento (y reasignar usuarios)
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEvento(@PathVariable Integer id, @RequestBody EventoDTO dto) {
        try {
            Evento evento = this.eventoService.actualizarEvento(id, dto);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el evento: " + e.getMessage());
        }
    }

    // Eliminar evento
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEvento(@PathVariable Integer id) {
        try {
            this.eventoService.eliminarEvento(id);
            return ResponseEntity.ok("Evento eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el evento: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/contar-todos")
    public ResponseEntity<Long> contarTodosEventos() {
        long total = eventoService.contarTodosLosEventos();
        return ResponseEntity.ok(total);
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/activos")
    public List<Evento> listarEventosActivos() {
        return eventoService.obtenerEventosActivos();
    }

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/promedio-duracion-horas")
    public Double promedioDuracionHoras() {
        return eventoService.obtenerPromedioDuracionHoras();
    }
}