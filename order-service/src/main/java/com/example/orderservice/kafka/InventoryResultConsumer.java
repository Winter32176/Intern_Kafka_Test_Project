package com.example.orderservice.kafka;

import com.example.orderservice.event.InventoryResultEvent;
import com.example.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryResultConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(InventoryResultConsumer.class);

    private final OrderService orderService;

    public InventoryResultConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${app.kafka.topics.inventory-result}")
    public void consume(InventoryResultEvent event) {
        boolean updated = orderService.updateStatus(
                event.orderId(),
                event.status()
        );

        if (updated) {
            log.info(
                    "Order status updated: orderId={}, status={}",
                    event.orderId(),
                    event.status()
            );
        } else {
            log.warn(
                    "Order not found for inventory result: orderId={}, status={}",
                    event.orderId(),
                    event.status()
            );
        }
    }
}