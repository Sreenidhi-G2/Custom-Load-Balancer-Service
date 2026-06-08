package com.sreenidhi.load.balancer.service.algorithm;

import com.sreenidhi.load.balancer.service.model.BackendServer;
import com.sreenidhi.load.balancer.service.registry.ServiceRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Comparator;
import java.util.List;

@Component
public class LeastConnectionsStrategy implements LoadBalancingStrategy {

    private final ServiceRegistry serviceRegistry;

    public LeastConnectionsStrategy(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public BackendServer selectServer(ServerWebExchange exchange) {

        List<BackendServer> healthyServers = serviceRegistry.getHealthyServers();

        if (healthyServers.isEmpty()) {
            throw new RuntimeException("No healthy backend servers available");
        }

        return healthyServers.stream()
                .min(Comparator.comparingInt(
                        server -> server.getActiveConnections().get()
                ))
                .orElseThrow();
    }
}