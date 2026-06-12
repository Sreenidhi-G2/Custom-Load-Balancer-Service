package com.sreenidhi.load.balancer.service.algorithm;

import com.sreenidhi.load.balancer.service.model.BackendServer;
import com.sreenidhi.load.balancer.service.registry.ServiceRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Component
public class ConsistentHashingStrategy implements LoadBalancingStrategy {

    private static final int VIRTUAL_NODES = 100;

//    private final ServiceRegistry serviceRegistry;
    private volatile SortedMap<Integer, BackendServer> hashRing = new TreeMap<>();

//    public ConsistentHashingStrategy(ServiceRegistry serviceRegistry) {
//        this.serviceRegistry = serviceRegistry;
//        this.hashRing = buildHashRing(serviceRegistry.getHealthyServers()); // build once at startup
//    }

    public void rebuildRing(List<BackendServer> servers) {  // ← accepts the list directly
        this.hashRing = buildHashRing(servers);
    }


    @Override
    public BackendServer selectServer(ServerWebExchange exchange) {
        SortedMap<Integer, BackendServer> ring = this.hashRing; // read once (volatile snapshot)

        if (ring.isEmpty()) {
            throw new RuntimeException("No healthy backend servers available");
        }

        String requestKey = getRequestKey(exchange);
        int requestHash = hash(requestKey);

        SortedMap<Integer, BackendServer> tailMap = ring.tailMap(requestHash);
        return tailMap.isEmpty() ? ring.get(ring.firstKey()) : tailMap.get(tailMap.firstKey());
    }

    private SortedMap<Integer, BackendServer> buildHashRing(List<BackendServer> servers) {

        SortedMap<Integer, BackendServer> hashRing = new TreeMap<>();

        for (BackendServer server : servers) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String virtualNodeKey = server.getId() + "#VN" + i;
                hashRing.put(hash(virtualNodeKey), server);
            }
        }

        return hashRing;
    }

    private String getRequestKey(ServerWebExchange exchange) {

        String userId = exchange.getRequest()
                .getHeaders()
                .getFirst("X-User-Id");

        if (userId != null && !userId.isBlank()) {
            return userId;
        }

        String clientIp = exchange.getRequest()
                .getRemoteAddress()
                .getAddress()
                .getHostAddress();

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        return clientIp + ":" + path;
    }

    private int hash(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(key.getBytes(StandardCharsets.UTF_8));

            int hash = 0;
            for (int i = 0; i < 4; i++) {
                hash = (hash << 8) | (bytes[i] & 0xFF);
            }

            return hash & 0x7fffffff;

        } catch (Exception e) {
            throw new RuntimeException("Failed to hash key", e);
        }
    }
}