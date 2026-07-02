package com.sreenidhi.load.balancer.service.controller;
import com.sreenidhi.load.balancer.service.model.BackendServer;
import com.sreenidhi.load.balancer.service.registry.ServiceRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistryController {

    private final ServiceRegistry serviceRegistry;

    public RegistryController(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody BackendServer server
    ) {

        serviceRegistry.registerServer(server);

        return ResponseEntity.ok(
                server.getId() + " registered successfully."
        );
    }
}