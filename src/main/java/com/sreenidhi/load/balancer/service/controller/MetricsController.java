package com.sreenidhi.load.balancer.service.controller;

import com.sreenidhi.load.balancer.service.model.BackendServer;
import com.sreenidhi.load.balancer.service.registry.ServiceRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MetricsController {

    private final ServiceRegistry serviceRegistry;

    public MetricsController(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @GetMapping("/metrics")
    public List<Map<String, Object>> metrics() {

        List<Map<String, Object>> response = new ArrayList<>();

        for (Map.Entry<String  , BackendServer> entry :
                serviceRegistry.getAllServers().entrySet()) {

            BackendServer server = entry.getValue();

            Map<String, Object> serverMetrics =
                    new HashMap<>();

            serverMetrics.put(
                    "serverId",
                    server.getId());

            serverMetrics.put(
                    "healthy",
                    server.isHealthy());

            serverMetrics.put(
                    "activeConnections",
                    server.getActiveConnections().get());

            serverMetrics.put(
                    "totalRequests",
                    server.getTotalRequests().get());

            response.add(serverMetrics);
        }

        return response;
    }
}