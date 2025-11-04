package io.spaceurgent.order.service.ai.infrastracture.db.mapper;

import io.spaceurgent.order.service.ai.domain.model.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(UUID.fromString(rs.getString("id")))
                .orderTime(rs.getTimestamp("order_time").toInstant())
                .customer(rs.getString("customer"))
                .product(rs.getString("product"))
                .quantity(rs.getInt("quantity"))
                .pricePerUnit(rs.getBigDecimal("price_per_unit"))
                .totalPrice(rs.getBigDecimal("total_price"))
                .build();
    }
}
