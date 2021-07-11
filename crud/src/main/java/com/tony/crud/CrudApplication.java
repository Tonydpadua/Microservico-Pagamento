package com.tony.crud;

import com.tony.crud.product.Product;
import com.tony.crud.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepository productRepository) {
		return args -> {
			initUsers(productRepository);
		};
	}

	private void initUsers(ProductRepository productRepository) {
		Product product = new Product(null, "Computer", 3, 400.00);
		productRepository.save(product);
	}
}
