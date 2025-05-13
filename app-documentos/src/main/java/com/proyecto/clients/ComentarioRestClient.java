package com.proyecto.clients;

import com.proyecto.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "comentario-service", url = "http://localhost:7070/app-comentarios", configuration = FeignConfig.class)
public interface ComentarioRestClient {

    @DeleteMapping("/comentarios/comentarios/{id}")
    void eliminarComentarioPorProyecto(@PathVariable("id")Integer id);
}
