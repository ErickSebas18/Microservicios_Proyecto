package com.proyecto.service;
import java.util.List;
import java.util.stream.Collectors;

import com.proyecto.clients.UsuarioRestClient;
import com.proyecto.db.ProyectoUsuario;
import com.proyecto.db.Tarea;
import com.proyecto.db.dtos.*;
import com.proyecto.repository.ProyectoUsuarioRepository;
import com.proyecto.repository.TareaRepository;
import com.proyecto.repository.TareaUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.db.Proyecto;
import com.proyecto.repository.ProyectoRepository;

import static com.proyecto.db.dtos.ProyectoConUsuariosDTO.toProyectoConUsuariosDTO;
import static com.proyecto.db.dtos.ProyectoDTO.toProyectoDTO;

@Service
public class ProyectoService{

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ProyectoUsuarioRepository proyectoUsuarioRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TareaUsuarioRepository tareaUsuarioRepository;

    @Autowired
    private UsuarioRestClient usuarioRestClient;

    @Autowired
    private ProyectoUsuarioRepository repository;

    //Crear un proyecto
    public ProyectoDTO guardarProyecto(CrearProyectoDTO proyectoDto){
        try{
            Proyecto proyecto = new Proyecto();
            proyecto.setId(null);
            proyecto.setTitulo(proyectoDto.getTitulo());
            proyecto.setDescripcion(proyectoDto.getDescripcion());
            proyecto.setFechaInicio(proyectoDto.getFechaInicio());
            proyecto.setFechaFin(proyectoDto.getFechaFin());
            proyecto.setEstado(proyectoDto.getEstado());

            proyecto = proyectoRepository.save(proyecto);

            for (Integer usuarioId : proyectoDto.getUsuarioIds()){
                ProyectoUsuario pu = new ProyectoUsuario();
                pu.setProyecto(proyecto);
                pu.setUsuarioId(usuarioId);
                proyectoUsuarioRepository.save(pu);
            }

            return toProyectoDTO(proyecto);
        } catch(Exception e){
            throw new RuntimeException("Error al crear el proyecto ");
        }

    }

    //Obtener todos los proyectos
    public List<ProyectoDTO> listarProyectos(){
        try{
            return proyectoRepository.findAll().stream().map(ProyectoDTO::toProyectoDTO).collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException("Error al listar los proyectos");
        }
    }

    //Obtener todos mis proyectos
    public List<ProyectoDTO> listarMisProyectos(Integer id){
        try{
            List<Proyecto> proyectos = proyectoUsuarioRepository.findProyectosByUsuarioId(id);
            return proyectos.stream()
                    .map(ProyectoDTO::toProyectoDTO)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException("Error al listar los proyectos");
        }
    }

    //Obtener proyecto por Id con usuarios y tareas
    public ProyectoConUsuariosDTO obtenerProyectoConUsuarios(Integer id){
        try{
            Proyecto proyecto = proyectoRepository.findById(id).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

            List<ProyectoUsuario> relaciones = proyectoUsuarioRepository.findByProyectoId(id);
            List<Integer> usuarioIds = relaciones.stream().map(ProyectoUsuario::getUsuarioId).toList();
            List<UsuarioDTO> usuarios = usuarioRestClient.findAll().stream().filter(u -> usuarioIds.contains(u.getId())).toList();

            return toProyectoConUsuariosDTO(proyecto, usuarios);
        } catch (Exception e){
            //e.printStackTrace();
            throw new RuntimeException("Error al obtener el proyecto con usuarios");
        }
    }

    //Actualizar Proyecto
    public ProyectoDTO actualizarProyecto(Integer id, CrearProyectoDTO dto){
        try{
            Proyecto proyecto = proyectoRepository.findById(id).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
            proyecto.setTitulo(dto.getTitulo());
            proyecto.setDescripcion(dto.getDescripcion());
            proyecto.setFechaInicio(dto.getFechaInicio());
            proyecto.setFechaFin(dto.getFechaFin());
            proyecto.setEstado(dto.getEstado());
            proyecto = proyectoRepository.save(proyecto);

            //Actualizar usuarios
            proyectoUsuarioRepository.deleteByProyecto(proyecto);
            for(Integer usuarioId : dto.getUsuarioIds()){
                ProyectoUsuario pu = new ProyectoUsuario();
                pu.setProyecto(proyecto);
                pu.setUsuarioId(usuarioId);
                proyectoUsuarioRepository.save(pu);
            }
            return  toProyectoDTO(proyecto);
        } catch (Exception e){
            throw new RuntimeException("Error al actualizar el proyecto");
        }
    }

    //Eliminar proyecto
    public void eliminarProyecto(Integer id){
        try{
            Proyecto proyecto = proyectoRepository.findById(id).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

            //Eliminar tareas y relaciones
            for(Tarea tarea : proyecto.getTareas()){
                tareaUsuarioRepository.deleteByTarea(tarea);
                tareaRepository.delete(tarea);
            }

            proyectoUsuarioRepository.deleteByProyecto(proyecto);
            proyectoRepository.delete(proyecto);
        } catch (Exception e){
            throw new RuntimeException("Error al eliminar el proyecto");
        }
    }
//    public List<Proyecto> getAllProjects() {
//        return proyectoRepository.findAllProjectedBy();
//    }
//
//    public List<ProyectoProjection> getAllByUser(Integer id) {
//        return proyectoRepository.findAllByResponsable(id);
//    }
//    /*
//    public List<ProyectoProjection> getAllProjectsByInvestigator(Integer id) {
//        return proyectoRepository.findAllByInvestigadores_Id(id);
//    }*/
//
//    public ProyectoProjection getById(Integer id) {
//        return proyectoRepository.findProjectedById(id);
//    }
//
//    public Proyecto saveProject(Proyecto proyecto) {
//        proyecto.setId(null);
//        return this.proyectoRepository.save(proyecto);
//    }
//
//    public Boolean deleteProjectById(Integer id) {
//        if (!this.proyectoRepository.existsById(id)) {
//            return false;
//        }
//        this.proyectoRepository.deleteById(id);
//        return true;
//    }
//
//    public String updateProject(Integer id, Proyecto proyectoActualizado) {
//        try {
//            return proyectoRepository.findById(id).map(proyecto -> {
//                boolean updated = false;
//
//                if (proyectoActualizado.getTitulo() != null
//                        && !proyectoActualizado.getTitulo().equals(proyecto.getTitulo())) {
//                    proyecto.setTitulo(proyectoActualizado.getTitulo());
//                    updated = true;
//                }
//                if (proyectoActualizado.getDescripcion() != null
//                        && !proyectoActualizado.getDescripcion().equals(proyecto.getDescripcion())) {
//                    proyecto.setDescripcion(proyectoActualizado.getDescripcion());
//                    updated = true;
//                }
//                if (proyectoActualizado.getEstado() != null
//                        && !proyectoActualizado.getEstado().equals(proyecto.getEstado())) {
//                    proyecto.setEstado(proyectoActualizado.getEstado());
//                    updated = true;
//                }
//                if (proyectoActualizado.getFechaInicio() != null
//                        && !proyectoActualizado.getFechaInicio().equals(proyecto.getFechaInicio())) {
//                    proyecto.setFechaInicio(proyectoActualizado.getFechaInicio());
//                    updated = true;
//                }
//                if (proyectoActualizado.getFechaFin() != null
//                        && !proyectoActualizado.getFechaFin().equals(proyecto.getFechaFin())) {
//                    proyecto.setFechaFin(proyectoActualizado.getFechaFin());
//                    updated = true;
//                }
//
//                if (proyectoActualizado.getResponsable() != null
//                        && !proyectoActualizado.getResponsable().equals(proyecto.getResponsable())) {
//                    proyecto.setResponsable(proyectoActualizado.getResponsable());
//                    updated = true;
//                }
//
////                if (proyectoActualizado.getInvestigadores() != null
////                        && !proyectoActualizado.getInvestigadores().equals(proyecto.getInvestigadores())) {
////                    proyecto.setInvestigadores(proyectoActualizado.getInvestigadores());
////                    updated = true;
////                }
//
//                if (updated) {
//                    proyectoRepository.save(proyecto);
//                    return "Proyecto actualizado correctamente";
//                } else {
//                    return "No se realizaron cambios en el proyecto";
//                }
//            }).orElse("Proyecto no encontrado con el id: " + id);
//        } catch (Exception e) {
//            return "Error al actualizar el proyecto: " + e.getMessage();
//        }
//    }

}
