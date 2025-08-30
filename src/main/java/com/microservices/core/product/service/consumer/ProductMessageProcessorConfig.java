package com.microservices.core.product.service.consumer;

import com.microservices.core.product.service.dto.ProductDTO;
import com.microservices.core.product.service.service.ProductService;
import com.microservices.core.util.api.event.Event;
import com.microservices.core.util.exceptions.EventProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ProductMessageProcessorConfig {

    @Autowired
    private ProductService productService;

    @Bean
    public Consumer<Event<Long, ProductDTO>> consumeEvent() {
        return event -> {
            log.debug("Processing message, created at {}", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE -> {
                    ProductDTO productDTO = event.getData();
                    log.debug("Creating product: {}", productDTO);
                    productService.createProduct(productDTO).block();

                }
                case DELETE -> {
                    Long productId = event.getKey();
                    log.debug("Delete product using ID: {}", productId);
                    productService.deleteProduct(productId).block();
                }
                default -> {
                    log.warn("Event type not supported: {}", event.getEventType());
                    throw new EventProcessingException("Event type not supported: " + event.getEventType());
                }
            }
        };
    }
}
