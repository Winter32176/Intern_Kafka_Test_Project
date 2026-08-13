package com.example.orderservice.event;

import com.example.orderservice.domain.OrderStatus;

import java.util.UUID;

public record InventoryResultEvent(
        UUID orderId,
        OrderStatus status
) {
}