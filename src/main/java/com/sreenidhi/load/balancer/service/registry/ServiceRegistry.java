package com.sreenidhi.load.balancer.service.registry;

import com.sreenidhi.load.balancer.service.model.BackendServer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class ServiceRegistry {

    private final List<BackendServer> servers = new CopyOnWriteArrayList<>();

    public ServiceRegistry() {

        servers.add(new BackendServer(
                "backend-1",
                "http://localhost:8081"
        ));

        servers.add(new BackendServer(
                "backend-2",
                "http://localhost:8082"
        ));

        servers.add(new BackendServer(
                "backend-3",
                "http://localhost:8083"
        ));
    }

    public List<BackendServer> getHealthyServers() {

        return servers.stream()
                .filter(BackendServer::isHealthy)
                .collect(Collectors.toList());
    }

    public List<BackendServer> getAllServers() {
        return servers;
    }
}