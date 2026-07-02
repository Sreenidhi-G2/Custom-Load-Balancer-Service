package com.sreenidhi.load.balancer.service.registry;


import com.sreenidhi.load.balancer.service.events.RegistryChangedEvent;
import com.sreenidhi.load.balancer.service.model.BackendServer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ServiceRegistry {

    private  final ApplicationEventPublisher eventPublisher;

    private final Map<String , BackendServer> servers =
            new ConcurrentHashMap<>();

    public ServiceRegistry(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    public void registerServer(BackendServer server) {
        servers.put(server.getId() ,  server);
        eventPublisher.publishEvent(new RegistryChangedEvent(getHealthyServers()));

        System.out.println("Registered Servers:");
        servers.values().forEach(System.out::println);
    }

    public void removeServer(String serverId) {
        servers.remove(serverId);
        eventPublisher.publishEvent(new RegistryChangedEvent(getHealthyServers()));

    }

    public List<BackendServer> getHealthyServers() {
        return servers.values().stream()
                .filter(BackendServer::isHealthy)
                .toList();
    }

    public Map<String,BackendServer> getAllServers() {
        return servers;
    }
}