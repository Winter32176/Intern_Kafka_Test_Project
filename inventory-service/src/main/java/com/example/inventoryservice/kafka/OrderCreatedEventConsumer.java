package com.example.inventoryservice.kafka;

import com.example.inventoryservice.event.OrderCreatedEvent;
import com.example.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(OrderCreatedEventConsumer.class);

    private final InventoryService inventoryService;

    public OrderCreatedEventConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "${app.kafka.topics.order-created}")
    public void consume(OrderCreatedEvent event) {
        log.info(
                """
                Processing order:
                OrderId: {}
                Product: {}
                Quantity: {}
                """,
                event.orderId(),
                event.product(),
                event.quantity()
        );

        inventoryService.processOrder(event);
    }
}
