package com.sreenidhi.load.balancer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Hooks;

@EnableScheduling
@SpringBootApplication
public class LoadBalancerServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(LoadBalancerServiceApplication.class, args);
	}

}
