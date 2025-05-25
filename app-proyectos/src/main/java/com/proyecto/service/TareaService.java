package com.proyecto.service;

import com.proyecto.db.Proyecto;
import com.proyecto.db.Tarea;
import com.proyecto.db.TareaUsuario;
import com.proyecto.db.dtos.CrearTareaDTO;
import com.proyecto.db.dtos.ProyectoDTO;
import com.proyecto.db.dtos.TareaConUsuariosDTO;
import com.proyecto.db.dtos.TareaDTO;
import com.proyecto.projections.TareaProjection;
import com.proyecto.repository.ProyectoRepository;
import com.proyecto.repository.TareaRepository;
import com.proyecto.repository.TareaUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TareaUsuarioRepository tareaUsuarioRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public TareaDTO crearTarea(CrearTareaDTO dto){
        try{
            Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId()).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

            Tarea tarea = new Tarea();
            tarea.setDescripcion(dto.getDescripcion());
            tarea.setEstado(dto.getEstado());
            tarea.setFechaAsignacion(dto.getFechaAsignacion());
            tarea.setFechaVencimiento(dto.getFechaVencimiento());
            tarea.setProyecto(proyecto);
            tarea = tareaRepository.save(tarea);

            if (dto.getUsuarioIds()!=null){
                for (Integer usuarioId : dto.getUsuarioIds()){
                    TareaUsuario tareaUsuario = new TareaUsuario();
                    tareaUsuario.setTarea(tarea);
                    tareaUsuario.setUsuarioId(usuarioId);
                    tareaUsuarioRepository.save(tareaUsuario);
                }
            }
            return TareaDTO.toTareaDTO(tarea);
        } catch (Exception e){
            throw new RuntimeException("Error al crear la tarea");
        }
    }

    public List<TareaDTO> listarTodas(){
        try{
            return tareaRepository.findAll().stream().map(TareaDTO::toTareaDTO).collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException("Error al listar todas las tareas");
        }
    }

    public List<TareaDTO> listarPorProyecto(Integer proyectoId) {
        try {
            List<Tarea> tareas = tareaRepository.findByProyectoId(proyectoId);
            return tareas.stream()
                    .map(t -> {
                        TareaDTO dto = TareaDTO.toTareaDTO(t);
                        dto.setUsuarios(this.obtenerUsuariosPorTarea(t.getId()));
                        return dto;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las tareas del proyecto");
        }
    }

    public List<TareaProjection> listarPorUsuario(Integer usuarioId) {
        try {
            List<TareaProjection> tareas = tareaUsuarioRepository.findTareasByUsuarioId(usuarioId);
            return tareas;
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las tareas del usuario");
        }
    }

    public List<Integer> obtenerUsuariosPorTarea(Integer tareaId){
        try{
            return tareaUsuarioRepository.findByTareaId(tareaId).stream().map(TareaUsuario::getUsuarioId).collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException("Error al obtener las tareas con usuarios");
        }
    }
    public TareaDTO actualizarTarea(Integer id, CrearTareaDTO dto){
        try{
            Tarea tarea = tareaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
            tarea.setDescripcion(dto.getDescripcion());
            tarea.setEstado(dto.getEstado());
            tarea.setFechaAsignacion(dto.getFechaAsignacion());
            tarea.setFechaVencimiento(dto.getFechaVencimiento());

            if (dto.getProyectoId()!=null && !tarea.getProyecto().getId().equals(dto.getProyectoId())){
                Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId()).orElseThrow(()-> new RuntimeException("Proyecto no encontrado"));
                tarea.setProyecto(proyecto);
            }
            tarea = tareaRepository.save(tarea);

            tareaUsuarioRepository.deleteByTarea(tarea);

            if (dto.getUsuarioIds() != null){
                for (Integer usuarioIds : dto.getUsuarioIds()){
                    TareaUsuario tareaUsuario = new TareaUsuario();
                    tareaUsuario.setTarea(tarea);
                    tareaUsuario.setUsuarioId(usuarioIds);
                    tareaUsuarioRepository.save(tareaUsuario);
                }
            }
            return TareaDTO.toTareaDTO(tarea);
        } catch (Exception e){
            throw new RuntimeException("Error al actualizar la tarea");
        }
    }

    public void eliminarTarea(Integer id){
        try{
            Tarea tarea = tareaRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró la tarea"));
            tareaUsuarioRepository.deleteByTarea(tarea);
            tareaRepository.delete(tarea);
        } catch (Exception e){
            throw new RuntimeException("Error al eliminar la tarea");
        }
    }

}