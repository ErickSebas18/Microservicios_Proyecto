package com.proyecto.controller;

import com.proyecto.db.EmailRequest;
import com.proyecto.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@EnableMethodSecurity
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public String enviarCorreo(@RequestBody EmailRequest emailRequest) {
        this.emailService.enviarCorreo(emailRequest.getPara(), emailRequest.getAsunto(), emailRequest.getMensaje());
        return "Correo enviado a " + emailRequest.getPara();
    }
}
