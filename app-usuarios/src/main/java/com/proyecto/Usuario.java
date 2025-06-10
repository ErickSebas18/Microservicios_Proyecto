package com.proyecto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.net.InetAddress;
import java.util.*;

@SpringBootApplication
public class Usuario {
    public static void main(String[] args) {
        SpringApplication.run(Usuario.class, args);
    }
}
