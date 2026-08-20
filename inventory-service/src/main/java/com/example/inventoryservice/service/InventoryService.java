package com.example.inventoryservice.service;

import com.example.inventoryservice.domain.InventoryStatus;
import com.example.inventoryservice.event.InventoryResultEvent;
import com.example.inventoryservice.event.OrderCreatedEvent;
import com.example.inventoryservice.kafka.InventoryResultProducer;
import org.springframework.stereotype.Service;

import com.example.inventoryservice.domain.InventoryStatus;
import com.example.inventoryservice.event.InventoryResultEvent;
import com.example.inventoryservice.event.OrderCreatedEvent;
import com.example.inventoryservice.kafka.InventoryResultProducer;
import com.example.inventoryservice.repository.ProcessedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class InventoryService {

    private static final Logger log =
            LoggerFactory.getLogger(InventoryService.class);

    private final InventoryResultProducer inventoryResultProducer;
    private final ProcessedMessageRepository processedMessageRepository;

    public InventoryService(
            InventoryResultProducer inventoryResultProducer,
            ProcessedMessageRepository processedMessageRepository
    ) {
        this.inventoryResultProducer = inventoryResultProducer;
        this.processedMessageRepository = processedMessageRepository;
    }

    @Transactional
    public void processOrder(OrderCreatedEvent order) {

        int inserted = processedMessageRepository.insertIfAbsent(
                order.orderId(),
                Instant.now()
        );

        if (inserted == 0) {
            log.info(
                    "Duplicate order ignored: orderId={}",
                    order.orderId()
            );
            return;
        }

         InventoryStatus status = order.quantity() <= 5
                ? InventoryStatus.AVAILABLE
                : InventoryStatus.OUT_OF_STOCK;

        InventoryResultEvent result =
                new InventoryResultEvent(order.orderId(), status);

        inventoryResultProducer.publish(result).join();

        log.info(
                "Inventory result published: orderId={}, status={}",
                order.orderId(),
                status
        );
    }
}