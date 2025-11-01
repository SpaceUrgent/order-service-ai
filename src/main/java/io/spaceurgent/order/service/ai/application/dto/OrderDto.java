package io.spaceurgent.order.service.ai.application.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderDto(
        UUID id,
        Instant orderTime,
        String customer,
        String product,
        Integer quantity,
        BigDecimal pricePerUnit,
        BigDecimal totalPrice
) {

    @Builder
    public OrderDto {
    }
}
