package com.proyecto.controller;

import com.proyecto.db.EmailRegistro;
import com.proyecto.service.EmailRegistroService;
import com.proyecto.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
@EnableMethodSecurity
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailRegistroService emailRegistroService;

    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarCorreo(@RequestBody EmailRegistro email) {
        try {
            emailService.enviarCorreo(email.getPara(), email.getAsunto(), email.getMensaje());
            emailRegistroService.save(email);
            return ResponseEntity.ok("Correo enviado a " + email.getPara());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al enviar el correo: " + e.getMessage());
        }
    }

    // Obtener todos los registros
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping
    public ResponseEntity<List<EmailRegistro>> getAllEmails() {
        List<EmailRegistro> list = emailRegistroService.getAll();
        return ResponseEntity.ok(list);
    }

    // Obtener cantidad total
    @PreAuthorize("hasAnyRole('admin_client', 'responsable_client', 'investigador_client')")
    @GetMapping("/count")
    public ResponseEntity<Long> getEmailCount() {
        long count = emailRegistroService.count();
        return ResponseEntity.ok(count);
    }
}
