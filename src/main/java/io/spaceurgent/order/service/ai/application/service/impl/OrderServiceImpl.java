package io.spaceurgent.order.service.ai.application.service.impl;

import io.spaceurgent.order.service.ai.application.dto.AddOrderRequestDto;
import io.spaceurgent.order.service.ai.application.dto.OrderDto;
import io.spaceurgent.order.service.ai.application.service.OrderService;
import io.spaceurgent.order.service.ai.domain.repository.ListOrdersCriteria;
import io.spaceurgent.order.service.ai.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.spaceurgent.order.service.ai.application.mapper.OrderMapper.toOrder;
import static io.spaceurgent.order.service.ai.application.mapper.OrderMapper.toOrderDto;
import static io.spaceurgent.order.service.ai.application.mapper.OrderMapper.toOrderDtoList;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDto addOrder(AddOrderRequestDto request) {
        assert request != null : "Add order request is required";
        final var order = orderRepository.addOrder(toOrder(request));
        return toOrderDto(order);
    }

    @Override
    public List<OrderDto> listOrders(ListOrdersCriteria criteria) {
        assert criteria != null : "List order criteria is required";
        final var orders = orderRepository.listOrders(criteria);
        return toOrderDtoList(orders);
    }
}
