package com.fawry.shipping_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShippingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingApiApplication.class, args);
	}

}
