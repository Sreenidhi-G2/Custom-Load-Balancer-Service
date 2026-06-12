package com.sreenidhi.load.balancer.service.registry;

import com.sreenidhi.load.balancer.service.algorithm.ConsistentHashingStrategy;
import com.sreenidhi.load.balancer.service.model.BackendServer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ServiceRegistry {

    private final List<BackendServer> servers =
            new CopyOnWriteArrayList<>();
    private ConsistentHashingStrategy hashingStrategy;

    public ServiceRegistry(ConsistentHashingStrategy hashingStrategy) {  // ← constructor injection, clean
        this.hashingStrategy = hashingStrategy;
        servers.add(new BackendServer("backend-1", "http://localhost:8081"));
        servers.add(new BackendServer("backend-2", "http://localhost:8082"));
        servers.add(new BackendServer("backend-3", "http://localhost:8083"));
        hashingStrategy.rebuildRing(getHealthyServers());  // ← build once at startup
    }
    @Autowired
    public void setHashingStrategy(ConsistentHashingStrategy hashingStrategy) {
        this.hashingStrategy = hashingStrategy;
    }

    public void registerServer(BackendServer server) {
        servers.add(server);
        hashingStrategy.rebuildRing(getHealthyServers());
    }

    public void removeServer(String serverId) {
        servers.removeIf(s -> s.getId().equals(serverId));
        hashingStrategy.rebuildRing(getHealthyServers());
    }

    public List<BackendServer> getHealthyServers() {
        return servers.stream()
                .filter(BackendServer::isHealthy)
                .toList();
    }

    public List<BackendServer> getAllServers() {
        return servers;
    }
}