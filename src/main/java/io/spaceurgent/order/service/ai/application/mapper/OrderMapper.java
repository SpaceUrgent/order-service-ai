package io.spaceurgent.order.service.ai.application.mapper;

import io.spaceurgent.order.service.ai.application.dto.AddOrderRequestDto;
import io.spaceurgent.order.service.ai.application.dto.OrderDto;
import io.spaceurgent.order.service.ai.domain.model.Order;

import java.util.List;

public final class OrderMapper {
    private OrderMapper() {
    }

    public static Order toOrder(AddOrderRequestDto requestDto) {
        return Order.builder()
                .orderTime(requestDto.orderTime())
                .customer(requestDto.customer())
                .product(requestDto.product())
                .quantity(requestDto.quantity())
                .pricePerUnit(requestDto.pricePerUnit())
                .totalPrice(requestDto.totalPrice())
                .build();
    }

    public static List<OrderDto> toOrderDtoList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toOrderDto)
                .toList();
    }

    public static OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderTime(order.getOrderTime())
                .customer(order.getCustomer())
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .pricePerUnit(order.getPricePerUnit())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
