package com.proyecto.clients;

import com.proyecto.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "documento-service", url = "http://localhost:7070/app-documentos", configuration = FeignConfig.class)
public interface DocumentoRestClient {

    @DeleteMapping("/documentos/eliminar-del-proyecto/{id}")
    void deleteDocumentsOnProject(@PathVariable("id")Integer id);
}
