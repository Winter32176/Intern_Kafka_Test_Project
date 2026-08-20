package com.example.inventoryservice.kafka;

import com.example.inventoryservice.event.OrderCreatedEvent;
import com.example.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventConsumer {

    private static int i = 0;

    private static final Logger log =
            LoggerFactory.getLogger(OrderCreatedEventConsumer.class);

    private final InventoryService inventoryService;

    public OrderCreatedEventConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "${app.kafka.topics.order-created}")
    public void consume(OrderCreatedEvent event) {

        log.info(
                "Processing order: orderId={}, product={}, quantity={}",
                event.orderId(),
                event.product(),
                event.quantity()
        );

       // if (i == 5) i = 0; else i++; // to increase chances

        if (java.util.concurrent.ThreadLocalRandom.current().nextBoolean() || i >= 2) {
            log.warn("Simulated processing failure: orderId={}", event.orderId());
            throw new RuntimeException(
                    "Simulated inventory processing failure"
            );
        }


        inventoryService.processOrder(event);

        log.info(
                "Order processed successfully: orderId={}",
                event.orderId()
        );
    }
}
