package com.proyecto.clients;

import com.proyecto.config.FeignConfig;
import com.proyecto.db.dtos.DocumentoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "documento-service", url = "http://localhost:9094", configuration = FeignConfig.class)
public interface DocumentoRestClient {

    @GetMapping
    List<DocumentoDto> findAll();

    @GetMapping("/documentos/{id}")
    DocumentoDto findById(@PathVariable("id")Integer id);
}
