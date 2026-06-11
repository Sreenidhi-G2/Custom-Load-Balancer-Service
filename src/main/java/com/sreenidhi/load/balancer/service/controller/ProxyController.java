package com.sreenidhi.load.balancer.service.controller;

import com.sreenidhi.load.balancer.service.algorithm.LoadBalancingStrategy;
import com.sreenidhi.load.balancer.service.algorithm.StrategyResolver;
import com.sreenidhi.load.balancer.service.model.BackendServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class ProxyController {

    private final WebClient webClient;
    private final StrategyResolver strategyResolver;

    public ProxyController(
            WebClient webClient,
            StrategyResolver strategyResolver
    ) {
        this.webClient = webClient;
        this.strategyResolver = strategyResolver;
    }

    @GetMapping("/api/test")
    public Mono<String> proxyRequest(
            ServerWebExchange exchange,
            @RequestHeader(
                    value = "X-LB-Strategy",
                    required = false
            ) String strategyName
    ) {

        LoadBalancingStrategy strategy =
                strategyResolver.resolve(strategyName);

        BackendServer server =
                strategy.selectServer(exchange);

        server.getActiveConnections().incrementAndGet();

        String targetUrl =
                server.getBaseUrl() + "/api/test";

        System.out.println(
                "Routing request to: " + server.getId()
        );

        return webClient
                .get()
                .uri(targetUrl)
                .retrieve()
                .bodyToMono(String.class)
                .doFinally(signal ->
                        server.getActiveConnections()
                                .decrementAndGet()
                );
    }
}