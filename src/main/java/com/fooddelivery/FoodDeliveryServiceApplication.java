package com.fooddelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fooddelivery.service.CustomersService;
import com.fooddelivery.service.RestaurantsService;

@SpringBootApplication
public class FoodDeliveryServiceApplication implements CommandLineRunner {
	
	@Autowired
	CustomersService cs;

	@Autowired
	RestaurantsService rs;
	
	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Hello Food Delivery Service");
	}

}
