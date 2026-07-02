package com.sreenidhi.load.balancer.service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class BackendServer {

    // getters
    private final String id;
    private final String baseUrl;
    private final AtomicLong totalRequests;
    // heartbeat / TTL bookkeeping — epoch millis of the last register or heartbeat check-in
    private final AtomicLong lastHeartbeat;
    //setters
    @Setter
    private volatile boolean healthy;
    private final AtomicInteger activeConnections;

    public BackendServer(String id, String baseUrl) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.healthy = true;
        this.activeConnections = new AtomicInteger(0);
        this.totalRequests = new AtomicLong(0);
        this.lastHeartbeat = new AtomicLong(System.currentTimeMillis());
    }

    public void incrementConnections() {
        activeConnections.incrementAndGet();
    }

    public void decrementConnections() {
        activeConnections.decrementAndGet();
    }

    public void touchHeartbeat() {
        lastHeartbeat.set(System.currentTimeMillis());
    }

    public boolean isExpired(long ttlMillis) {
        return System.currentTimeMillis() - lastHeartbeat.get() > ttlMillis;
    }
}