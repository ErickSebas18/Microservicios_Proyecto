package com.proyecto.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.proyecto.clients.*;
import com.proyecto.db.dtos.CrearProyectoDTO;
import com.proyecto.db.dtos.ProyectoConUsuariosDTO;
import com.proyecto.db.dtos.ProyectoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.db.Proyecto;
import com.proyecto.service.ProyectoService;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/proyectos")
@EnableMethodSecurity
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private DocumentoRestClient documentoRestClient;

    @PreAuthorize("hasRole('admin_client')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearProyecto(@RequestBody CrearProyectoDTO dto){
        try{
            ProyectoDTO proyecto = proyectoService.guardarProyecto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(proyecto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PreAuthorize("hasRole('admin_client')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listarProyectos(){
        try{
            return ResponseEntity.ok(proyectoService.listarProyectos());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PreAuthorize("hasRole('admin_client')")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProyectoConUsuarios(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(proyectoService.obtenerProyectoConUsuarios(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
        }
    }

    @PreAuthorize("hasRole('admin_client')")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarProyecto(@PathVariable Integer id, @RequestBody CrearProyectoDTO dto){
        try{
            ProyectoDTO actualizado = proyectoService.actualizarProyecto(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PreAuthorize("hasRole('admin_client')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminarProyecto(@PathVariable Integer id){
        try{
            proyectoService.eliminarProyecto(id);
            this.documentoRestClient.deleteDocumentsOnProject(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }


//    @Autowired
//    private UsuarioRestClient usuarioRestClient;
//
//    @Autowired
//    private TareaRestClient tareaRestClient;
//
//    @Autowired
//    private DocumentoRestClient documentoRestClient;
//
//    @Autowired
//    private ProyectoInvestigadorRestClient proyectoInvestigadorRestClient;
//
//    @Autowired
//    private TareaInvestigadorRestClient tareaInvestigadorRestClient;
//
//    @GetMapping
//    @PreAuthorize("hasRole('admin_client')")
//    public ResponseEntity<List<ProyectoDTO>> findAllProjects() {
//        try {
//            return new ResponseEntity<>(this.proyectoService.getAllProjects().stream().map(
//                    proyecto -> {
//                        var usuario = usuarioRestClient.findById(proyecto.getResponsable());
//                        ProyectoDTO p = new ProyectoDTO();
//                        p.setId(proyecto.getId());
//                        p.setEstado(proyecto.getEstado());
//                        p.setDescripcion(proyecto.getDescripcion());
//                        p.setTitulo(proyecto.getTitulo());
//                        p.setFechaFin(proyecto.getFechaFin());
//                        p.setFechaInicio(proyecto.getFechaInicio());
//                        p.setResponsable(usuario.getId());
//                        p.setResponsableNombre(usuario.getNombre());
//                        return p;
//                    }
//            ).collect(Collectors.toList()), null, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping(path = "/responsable-proyectos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> findAllProjectsByUser(@PathVariable Integer id) {
//        try {
//            return new ResponseEntity<>(this.proyectoService.getAllByUser(id).stream().map(
//                    proyecto -> {
//                        var usuario = usuarioRestClient.findById(id);
//                        ProyectoDTO p = new ProyectoDTO();
//                        p.setId(proyecto.getId());
//                        p.setEstado(proyecto.getEstado());
//                        p.setDescripcion(proyecto.getDescripcion());
//                        p.setTitulo(proyecto.getTitulo());
//                        p.setFechaFin(proyecto.getFechaFin());
//                        p.setFechaInicio(proyecto.getFechaInicio());
//                        p.setResponsable(usuario.getId());
//                        p.setResponsableNombre(usuario.getNombre());
//                        return p;
//                    }
//            ).collect(Collectors.toList()), null, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
///*
//    @GetMapping(path = "/investigador-proyectos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> findAllProjectsByInvestigator(@PathVariable Integer id) {
//        try {
//            return new ResponseEntity<>(this.proyectoService.getAllProjectsByInvestigator(id), null, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }*/
//
//    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> findProjectById(@PathVariable Integer id) {
//        var proyecto = proyectoService.getById(id);
//        if (proyecto != null) {
//            var usuario = usuarioRestClient.findById(proyecto.getResponsable());
//            ProyectoDTO p = new ProyectoDTO();
//            p.setId(proyecto.getId());
//            p.setEstado(proyecto.getEstado());
//            p.setDescripcion(proyecto.getDescripcion());
//            p.setTitulo(proyecto.getTitulo());
//            p.setFechaFin(proyecto.getFechaFin());
//            p.setFechaInicio(proyecto.getFechaInicio());
//            p.setResponsable(usuario.getId());
//            p.setResponsableNombre(usuario.getNombre());
//            return new ResponseEntity<>(p, null, HttpStatus.OK);
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
//
//    @PreAuthorize("hasRole('admin_client')")
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> insertProject(@RequestBody Proyecto proyecto) {
//        try {
//            return new ResponseEntity<>(proyectoService.saveProject(proyecto), null, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
//        }
//    }
//
//    @PreAuthorize("hasRole('admin_client')")
//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
//        try {
//            if (this.proyectoService.deleteProjectById(id)) {
//                this.tareaRestClient.deleteTasksOnProject(id);
//                this.documentoRestClient.deleteDocumentsOnProject(id);
//                //this.proyectoInvestigadorRestClient.eliminarPorProyecto(id);
//                this.tareaInvestigadorRestClient.eliminarPorProyecto(id);
//                return ResponseEntity.ok("Proyecto eliminado");
//            } else {
//                return ResponseEntity.badRequest().build();
//            }
//        }catch (Exception e){
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @PutMapping(path = "/{id}")
//    public ResponseEntity<?> updateProject(@PathVariable Integer id, @RequestBody Proyecto proyectoActualizado) {
//        try {
//            return new ResponseEntity<>(this.proyectoService.updateProject(id, proyectoActualizado), null,
//                    HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }

}
