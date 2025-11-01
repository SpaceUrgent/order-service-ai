package io.spaceurgent.order.service.ai.application.service;

import io.spaceurgent.order.service.ai.application.dto.AddOrderRequestDto;
import io.spaceurgent.order.service.ai.application.dto.OrderDto;
import io.spaceurgent.order.service.ai.domain.repository.ListOrdersCriteria;

import java.util.List;

public interface OrderService {

    OrderDto addOrder(AddOrderRequestDto request);

    List<OrderDto> listOrders(ListOrdersCriteria criteria);
}
