package com.example.orderservice.event;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        Long customerId,
        String product,
        Integer quantity,
        Instant createdTime
) {
}