package com.ordersystem.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication(scanBasePackages = {"com.ordersystem.controller,com.ordersystem.service,com.ordersystem.entity,com.ordersystem.repository"})
@EntityScan(basePackages = {"com.ordersystem.entity"})
@EnableJpaRepositories(basePackages = {"com.ordersystem.repository"})
public class WaterOrderingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterOrderingSystemApplication.class, args);
	}

}
