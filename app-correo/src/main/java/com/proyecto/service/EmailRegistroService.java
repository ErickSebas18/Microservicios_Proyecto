package com.proyecto.service;

import com.proyecto.db.EmailRegistro;
import com.proyecto.repository.EmailRegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailRegistroService {

    @Autowired
    private EmailRegistroRepository emailRegistroRepository;

    // Guardar un nuevo email
    public EmailRegistro save(EmailRegistro emailRegistro) {
        return emailRegistroRepository.save(emailRegistro);
    }

    // Obtener todos los registros
    public List<EmailRegistro> getAll() {
        return emailRegistroRepository.findAll();
    }

    // Obtener la cantidad de registros
    public long count() {
        return emailRegistroRepository.count();
    }
}
