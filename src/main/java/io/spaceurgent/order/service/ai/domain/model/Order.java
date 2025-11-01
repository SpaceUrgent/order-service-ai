package io.spaceurgent.order.service.ai.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Order {
    private UUID id;
    private Instant orderTime;
    private String customer;
    private String product;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
}
