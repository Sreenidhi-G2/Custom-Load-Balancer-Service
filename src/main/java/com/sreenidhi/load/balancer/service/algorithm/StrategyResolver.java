package com.sreenidhi.load.balancer.service.algorithm;

import org.springframework.stereotype.Component;

@Component
public class StrategyResolver {

    private final LeastConnectionsStrategy leastConnectionsStrategy;
    private final ConsistentHashingStrategy consistentHashingStrategy;

    public StrategyResolver(
            LeastConnectionsStrategy leastConnectionsStrategy,
            ConsistentHashingStrategy consistentHashingStrategy
    ) {
        this.leastConnectionsStrategy = leastConnectionsStrategy;
        this.consistentHashingStrategy = consistentHashingStrategy;
    }

    public LoadBalancingStrategy resolve(String algorithm) {

        if (algorithm == null) {
            return leastConnectionsStrategy;
        }

        return switch (algorithm.toLowerCase()) {

            case "consistent-hashing" ->
                    consistentHashingStrategy;

            case "least-connections" ->
                    leastConnectionsStrategy;

            default ->
                    leastConnectionsStrategy;
        };
    }
}