package com.example.orderservice.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        Long customerId,
        String product,
        Integer quantity,
        Instant createdTime
) {
}