package com.proyecto.controller;

import com.proyecto.db.dtos.CrearTareaDTO;
import com.proyecto.db.dtos.TareaConUsuariosDTO;
import com.proyecto.db.dtos.TareaDTO;
import com.proyecto.projections.TareaProjection;
import com.proyecto.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("/tareas")
@EnableMethodSecurity
public class TareaController {

    @Autowired
    private TareaService tareaService;

    // Crear tarea
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TareaDTO> crearTarea(@RequestBody CrearTareaDTO crearTareaDTO) {
        return ResponseEntity.ok(tareaService.crearTarea(crearTareaDTO));
    }

    // Listar todas las tareas
    @GetMapping
    public ResponseEntity<List<TareaDTO>> listarTareas() {
        return ResponseEntity.ok(tareaService.listarTodas());
    }

    // Obtener tarea por ID
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerTarea(@PathVariable Integer id) {
        return ResponseEntity.ok(tareaService.listarPorProyecto(id));
    }
    // Obtener tarea por usuario
    @GetMapping(path = "/mis-tareas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerMisTarea(@PathVariable Integer id) {
        return ResponseEntity.ok(tareaService.listarPorUsuario(id));
    }

    @GetMapping(path = "/mis-tareas-responsable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerResponsableTareas(@PathVariable Integer id) {
        List<TareaProjection> tareas = tareaService.obtenerTareasProyectadasPorProyecto(id);

        if (tareas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(tareas); // 200 OK con la lista de tareas
    }

    // Actualizar tarea
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TareaDTO> actualizarTarea(@PathVariable Integer id, @RequestBody CrearTareaDTO tareaActualizada) {
        return ResponseEntity.ok(tareaService.actualizarTarea(id, tareaActualizada));
    }

    // Eliminar tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Integer id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    // Listar tarea con usuarios asociados
    @GetMapping(path = "/{id}/tarea-usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarTareaConUsuarios(@PathVariable Integer id) {
        return ResponseEntity.ok(tareaService.obtenerUsuariosPorTarea(id));
    }
}