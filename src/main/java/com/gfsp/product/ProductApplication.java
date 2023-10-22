package com.gfsp.product;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@OpenAPIDefinition(info = @Info(
	title = "Spring Boot documentation",
	description = "Spring Boot documentation",
	version = "v1.0",
	contact = @Contact(
		name = "Mohammad Darwish",
		email = "mohamdiddd@gmail.com"
	),
	license = @License(
		name = "Apache 2.0"
	)
)
)
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}
