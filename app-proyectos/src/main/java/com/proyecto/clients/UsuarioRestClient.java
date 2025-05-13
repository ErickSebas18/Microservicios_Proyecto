package com.proyecto.clients;

import com.proyecto.config.FeignConfig;
import com.proyecto.db.dtos.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "usuario-service", url = "http://localhost:7070/app-usuarios/usuarios", configuration = FeignConfig.class)
public interface UsuarioRestClient {

    @GetMapping
    List<UsuarioDTO> findAll();

    @GetMapping("/{id}")
    UsuarioDTO findById(@PathVariable("id")Integer id);
}
