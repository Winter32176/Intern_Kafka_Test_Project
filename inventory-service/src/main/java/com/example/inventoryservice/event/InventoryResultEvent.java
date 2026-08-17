package com.example.inventoryservice.event;

import com.example.inventoryservice.domain.InventoryStatus;

import java.util.UUID;

public record InventoryResultEvent(
        UUID orderId,
        InventoryStatus status
) {
}
