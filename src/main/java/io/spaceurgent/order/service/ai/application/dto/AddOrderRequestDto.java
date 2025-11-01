package io.spaceurgent.order.service.ai.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record AddOrderRequestDto(
        @NotNull
        Instant orderTime,
        @NotBlank
        String customer,
        @NotBlank
        String product,
        @NotNull @Positive
        Integer quantity,
        @NotNull @Positive
        BigDecimal pricePerUnit,
        @NotNull @Positive
        BigDecimal totalPrice
) {
}
