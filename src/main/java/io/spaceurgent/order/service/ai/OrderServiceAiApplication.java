package io.spaceurgent.order.service.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OrderServiceAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceAiApplication.class, args);
	}
}
