package com.example.inventoryservice.kafka;

import com.example.inventoryservice.event.InventoryResultEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class InventoryResultProducer {

    private final KafkaTemplate<String, InventoryResultEvent> kafkaTemplate;
    private final String inventoryResultTopic;

    public InventoryResultProducer(
            KafkaTemplate<String, InventoryResultEvent> kafkaTemplate,
            @Value("${app.kafka.topics.inventory-result}") String inventoryResultTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.inventoryResultTopic = inventoryResultTopic;
    }

    public CompletableFuture<SendResult<String, InventoryResultEvent>> publish(
            InventoryResultEvent event
    ) {
        String key = event.orderId().toString();

        return kafkaTemplate.send(
                inventoryResultTopic,
                key,
                event
        );
    }
}
