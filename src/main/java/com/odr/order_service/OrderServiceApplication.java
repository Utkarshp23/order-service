package com.odr.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	// @Bean
	// //Queries the Eureka Server to resolve service names to actual hosts and ports.
	// @LoadBalanced  
    // public RestTemplate restTemplate() {
    //     return new RestTemplate();
    // }

	// @Bean
	// @LoadBalanced
    // public RestClient restClient() {
    //     return RestClient.builder().build();	
    // }

	@Bean
	@LoadBalanced
	public RestClient.Builder restClientBuilder() {
		return RestClient.builder();
	}

}
