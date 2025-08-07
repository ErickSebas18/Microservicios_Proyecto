package com.proyecto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "consul.enabled", havingValue = "true", matchIfMissing = true)
@SpringBootApplication
@EnableFeignClients
public class AgendaLifeCycle implements ApplicationListener<WebServerInitializedEvent> {

    @Value("${consul.host:localhost}")
    private String consulHost;

    @Value("${consul.port:8500}")
    private int consulPort;

    @Value("${consul.token:}")
    private String consulToken;

    private final String serviceName = "app-agenda";

    private String serviceId;
    @Value("${server.port}")
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
        registerService();
    }

    private void registerService() {
        try {
            this.serviceId = UUID.randomUUID().toString();
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            String healthCheckUrl = "http://" + ipAddress + ":" + port + "/actuator/health";

            Map<String, Object> service = new HashMap<>();
            service.put("ID", serviceId);
            service.put("Name", serviceName);
            service.put("Address", ipAddress);
            service.put("Port", port);
            service.put("Tags", List.of(
                    "traefik.enable=true",
                    "traefik.http.routers." + serviceName + ".rule=PathPrefix(`/" + serviceName + "`)",
                    "traefik.http.routers." + serviceName + ".middlewares=" + serviceName,
                    "traefik.http.middlewares." + serviceName + ".stripPrefix.prefixes=/" + serviceName
            ));

            Map<String, String> check = new HashMap<>();
            check.put("HTTP", healthCheckUrl);
            check.put("Interval", "10s");
            check.put("DeregisterCriticalServiceAfter", "20s");
            service.put("Check", check);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            if (!consulToken.isEmpty()) {
                headers.add("X-Consul-Token", consulToken);
            }

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(service, headers);
            String consulUrl = "http://" + consulHost + ":" + consulPort + "/v1/agent/service/register";

            ResponseEntity<String> response = restTemplate.exchange(consulUrl, HttpMethod.PUT, request, String.class);
            System.out.println("Consul Registration Response: " + response.getBody());

        } catch (Exception e) {
            System.err.println("Error registering service with Consul: " + e.getMessage());
        }
    }

    private void deregisterService() {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (!consulToken.isEmpty()) {
                headers.add("X-Consul-Token", consulToken);
            }

            HttpEntity<Void> request = new HttpEntity<>(headers);
            String consulUrl = "http://" + consulHost + ":" + consulPort + "/v1/agent/service/deregister/" + serviceId;
            restTemplate.exchange(consulUrl, HttpMethod.PUT, request, String.class);
            System.out.println("Service deregistered from Consul.");
        } catch (Exception e) {
            System.err.println("Error deregistering service from Consul: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AgendaLifeCycle.class, args);
    }
}
