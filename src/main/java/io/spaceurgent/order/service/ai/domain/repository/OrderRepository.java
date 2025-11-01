package io.spaceurgent.order.service.ai.domain.repository;

import io.spaceurgent.order.service.ai.domain.model.Order;

import java.util.List;

public interface OrderRepository {

    Order addOrder(Order order);

    List<Order> listOrders(ListOrdersCriteria criteria);
}
