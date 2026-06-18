package com.sreenidhi.load.balancer.service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class BackendServer {

    // getters
    private final String id;
    private final String baseUrl;
    //setters
    @Setter
    private volatile boolean healthy;
    private final AtomicInteger activeConnections;

    public BackendServer(String id, String baseUrl) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.healthy = true;
        this.activeConnections = new AtomicInteger(0);
    }

    public void incrementConnections() {
        activeConnections.incrementAndGet();
    }

    public void decrementConnections() {
        activeConnections.decrementAndGet();
    }
}