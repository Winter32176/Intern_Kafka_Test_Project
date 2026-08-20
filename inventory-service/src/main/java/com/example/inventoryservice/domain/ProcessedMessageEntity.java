package com.example.inventoryservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "processed_messages")
public class ProcessedMessageEntity {

    @Id
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "processed_at", nullable = false)
    private Instant processedAt;

    protected ProcessedMessageEntity() {
    }

    public ProcessedMessageEntity(UUID orderId, Instant processedAt) {
        this.orderId = orderId;
        this.processedAt = processedAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }
}