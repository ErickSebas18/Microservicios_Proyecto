package com.proyecto.clients;

import com.proyecto.config.FeignConfig;
import com.proyecto.db.dtos.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "usuario-service", url = "http://localhost:9090", configuration = FeignConfig.class)
public interface UsuarioRestClient {

    @GetMapping
    List<UsuarioDto> findAll();

    @GetMapping("/usuarios/{id}")
    UsuarioDto findById(@PathVariable("id")Integer id);
}
