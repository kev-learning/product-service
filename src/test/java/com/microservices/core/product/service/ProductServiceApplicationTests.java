package com.microservices.core.product.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"eureka.client.enabled=false"})
@ImportAutoConfiguration(exclude = KafkaAutoConfiguration.class)
class ProductServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
