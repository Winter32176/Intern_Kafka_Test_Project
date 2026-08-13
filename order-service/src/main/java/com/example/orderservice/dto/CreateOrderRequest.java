package com.example.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequest(
        Long customerId,
        @NotNull
        String product,
        @NotNull
        @Positive
        Integer quantity
) { }