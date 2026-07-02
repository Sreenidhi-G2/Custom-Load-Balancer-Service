package com.sreenidhi.load.balancer.service.events;


import com.sreenidhi.load.balancer.service.model.BackendServer;

import java.util.List;

public class RegistryChangedEvent {

    private final List<BackendServer> healthyServers;

    public RegistryChangedEvent(List<BackendServer> healthyServers) {
        this.healthyServers = healthyServers;
    }

    public List<BackendServer> getHealthyServers() {
        return healthyServers;
    }
}