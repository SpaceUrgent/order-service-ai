package io.spaceurgent.order.service.ai.infrastracture.db.impl;

import io.spaceurgent.order.service.ai.domain.model.Order;
import io.spaceurgent.order.service.ai.domain.repository.ListOrdersCriteria;
import io.spaceurgent.order.service.ai.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private static final String INSERT_ORDER_SQL = """
            INSERT INTO orders (order_time, customer, product, quantity, price_per_unit, total_price)
            VALUES (?,?,?,?,?,?)
            RETURNING id
            """;

    private static final String SELECT_ORDERS_SQL = """
            SELECT * FROM orders WHERE 1=1
            """;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Order> orderRowMapper;

    @Override
    public Order addOrder(Order order) {
        assert order != null : "Order is required";
        final var id = jdbcTemplate.query(connection -> getInsertOrderPreparedStatement(order, connection), idExtractor());
        order.setId(id);
        return order;
    }

    @Override
    public List<Order> listOrders(ListOrdersCriteria criteria) {
        assert criteria != null : "Criteria is required";
        final var sql = new StringBuilder(SELECT_ORDERS_SQL);
        final var params = new ArrayList<>();
        if (criteria.product() != null) {
            sql.append(" AND product = ?");
            params.add(criteria.product());
        }
        if (criteria.customer() != null) {
            sql.append(" AND customer = ?");
            params.add(criteria.customer());
        }
        return jdbcTemplate.query(sql.toString(), orderRowMapper, params.toArray());
    }

    private static ResultSetExtractor<UUID> idExtractor() {
        return rs -> {
            rs.next();
            return UUID.fromString(rs.getString("id"));
        };
    }

    private  PreparedStatement getInsertOrderPreparedStatement(Order order, Connection connection) throws SQLException {
        var statement = connection.prepareStatement(INSERT_ORDER_SQL);
        statement.setTimestamp(1, Timestamp.from(order.getOrderTime()));
        statement.setString(2, order.getCustomer());
        statement.setString(3, order.getProduct());
        statement.setInt(4, order.getQuantity());
        statement.setBigDecimal(5, order.getPricePerUnit());
        statement.setBigDecimal(6, order.getTotalPrice());
        return statement;
    }
}
