# Load Balancer Service

A high-performance Layer 7 Load Balancer built with Spring Boot WebFlux to explore distributed systems concepts such as request routing, health monitoring, and fault tolerance.

## Features

- Least Connections load balancing
- Consistent Hashing with virtual nodes
- Reactive request proxying using Spring WebFlux
- Automatic backend health monitoring
- Dynamic exclusion of unhealthy servers
- Non-blocking architecture using WebClient

## Benchmark Results

### 1000 Concurrent Users
- Throughput: **12,615 requests/sec**
- Requests Served: **378,936**
- Average Latency: **68ms**
- Failure Rate: **0.40%**

### 5000 Concurrent Users
- Throughput: **7,635 requests/sec**
- Requests Served: **240,539**
- Average Latency: **54ms**
- Failure Rate: **1.95%**

## Tech Stack

- Java 23
- Spring Boot
- Spring WebFlux
- Reactor Netty
- WebClient
- Lombok
- k6

## Phase 2 Roadmap

- Dynamic Service Discovery
- Circuit Breaker Support
- Metrics & Monitoring (Actuator)
- Prometheus & Grafana Integration
- Docker & Kubernetes Deployment
- Optimized Consistent Hashing Ring

## Learning Goals

This project is focused on understanding:
- Load Balancing Algorithms
- Reactive Systems
- Concurrent Programming
- Health Monitoring
- Fault Tolerance
- Distributed Systems Design

---

