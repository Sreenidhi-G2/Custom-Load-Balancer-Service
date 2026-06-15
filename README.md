# Load Balancer Service

A custom Layer 7 Load Balancer built from scratch using Spring Boot and WebFlux to understand how modern distributed systems route traffic across backend services.

## Why This Project?

Most developers use load balancers such as NGINX, HAProxy, Envoy, or cloud-managed solutions without understanding how they work internally.

This project aims to implement the core building blocks of a production-grade load balancer from first principles and explore concepts such as:

* Request routing
* Load balancing algorithms
* Service discovery
* Health monitoring
* Rate limiting
* Concurrency handling
* Observability and metrics

## Current Features

* Backend server registry
* Least Connections load balancing
* Consistent Hashing with virtual nodes
* Thread-safe connection tracking
* Spring WebFlux integration
* Multiple backend server support

## Architecture

```text
Client
   │
   ▼
Load Balancer (Spring Boot)
   │
   ▼
Load Balancing Strategy
   ├── Least Connections
   └── Consistent Hashing
   │
   ▼
Backend Servers
   ├── Backend-1
   ├── Backend-2
   └── Backend-3
```

## Tech Stack

* Java 21+
* Spring Boot
* Spring WebFlux
* Gradle

## Planned Features

* Reverse proxy routing
* Active health checks
* Dynamic service discovery
* Rate limiting
* Request analytics
* Metrics and monitoring
* Prometheus integration
* Grafana dashboards
* Docker deployment
* Kubernetes deployment
* Benchmarking and performance testing

## Learning Goals

This project is being built as a hands-on exploration of:

* Distributed Systems
* Backend Infrastructure
* Network Routing
* Scalability
* Fault Tolerance
* Concurrent Programming
* System Design

## Status

🚧 Currently under active development.

The goal is to gradually evolve this project from a simple load balancer into a production-inspired traffic management system.

## Author

Sreenidhi G
