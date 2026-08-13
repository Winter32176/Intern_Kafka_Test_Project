package com.example.inventoryservice.service;

import com.example.inventoryservice.domain.InventoryStatus;
import com.example.inventoryservice.event.InventoryResultEvent;
import com.example.inventoryservice.event.OrderCreatedEvent;
import com.example.inventoryservice.kafka.InventoryResultProducer;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryResultProducer inventoryResultProducer;

    public InventoryService(InventoryResultProducer inventoryResultProducer) {
        this.inventoryResultProducer = inventoryResultProducer;
    }

    public void processOrder(OrderCreatedEvent order) {
        InventoryStatus status = order.quantity() <= 5
                ? InventoryStatus.AVAILABLE
                : InventoryStatus.OUT_OF_STOCK;

        InventoryResultEvent result = new InventoryResultEvent(
                order.orderId(),
                status
        );

        inventoryResultProducer.publish(result).join();
    }
}