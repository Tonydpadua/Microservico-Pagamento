package com.tonydpadua.payment;

import com.tonydpadua.payment.product.Product;
import com.tonydpadua.payment.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(ProductRepository productRepository) {
//		return args -> {
//			initUsers(productRepository);
//		};
//	}
//
//	private void initUsers(ProductRepository productRepository) {
//		Product product = new Product(null, 3);
//		productRepository.save(product);
//	}
}
