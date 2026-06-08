package com.sreenidhi.load.balancer.service.algorithm;

import com.sreenidhi.load.balancer.service.model.BackendServer;
import org.springframework.web.server.ServerWebExchange;

public interface LoadBalancingStrategy {

    BackendServer selectServer(ServerWebExchange exchange);
}