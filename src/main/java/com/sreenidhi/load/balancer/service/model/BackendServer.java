package com.sreenidhi.load.balancer.service.model;

import java.util.concurrent.atomic.AtomicInteger;

public class BackendServer {

    private final String id;
    private final String baseUrl;
    private volatile boolean healthy;
    private final AtomicInteger activeConnections;

    public BackendServer(String id, String baseUrl) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.healthy = true;
        this.activeConnections = new AtomicInteger(0);
    }

    public String getId() {
        return id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public AtomicInteger getActiveConnections() {
        return activeConnections;
    }

    public void incrementConnections() {
        activeConnections.incrementAndGet();
    }

    public void decrementConnections() {
        activeConnections.decrementAndGet();
    }
}