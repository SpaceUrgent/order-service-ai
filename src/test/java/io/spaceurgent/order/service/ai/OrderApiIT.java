package io.spaceurgent.order.service.ai;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spaceurgent.order.service.ai.application.dto.AddOrderRequestDto;
import io.spaceurgent.order.service.ai.application.dto.OrderDto;
import io.spaceurgent.order.service.ai.domain.model.Order;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresContainerConfig.class)
public class OrderApiIT {

    private static final String ADD_ORDER_JSON = """
            {
                "orderTime" : "2025-11-01T01:07:22.086191+02:00",
                "customer" : "Ben",
                "product" : "Water filter",
                "quantity" : 6,
                "pricePerUnit" : 4.99,
                "totalPrice" : 29.94
            }
            """;

    private static final String INSERT_ORDERS_SQL = """
            INSERT INTO orders (order_time, customer, product, quantity, price_per_unit, total_price)\s
            VALUES\s
            ('2025-10-15T13:07:22.086191+02:00', 'Adam', 'Light bulb', 10, 0.5, 5),
            ('2025-11-01T10:07:22.086191+02:00', 'Adam', 'Screwdriver', 2, 1.20, 2.40),
            ('2025-9-01T17:30:22.086191+02:00', 'Fred', 'Light bulb', 20, 0.5, 10),
            ('2025-10-01T10:07:22.086191+02:00', 'Fred', 'Screwdriver', 1, 1.20, 1.20),
            ('2025-10-01T10:07:22.086191+02:00', 'Fred', 'Water filter', 10, 4.99, 49.90)
            """;

    private static final String DELETE_ORDERS_SQL = """
            DELETE FROM orders;
            """;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update(DELETE_ORDERS_SQL);
        jdbcTemplate.update(INSERT_ORDERS_SQL);
    }

    @Test
    public void addOrder() throws Exception {
        final var responseBody = sendAddOrderRequest();
        final var givenRequestDto = objectMapper.readValue(ADD_ORDER_JSON, AddOrderRequestDto.class);
        final var order = objectMapper.readValue(responseBody, OrderDto.class);
        assertAddOrderRequestDtoMatchesToOrder(givenRequestDto, order);
        assertThat(orderExistsById(order.id())).isTrue();
    }

    @ParameterizedTest
    @MethodSource("listOrdersTestCases")
    public void listOrders(ListOrdersTestCase testCase) throws Exception {
        final var params = new LinkedMultiValueMap<String, String>();
        final var assertions = new ArrayList<Consumer<List<Order>>>();
        fillTestCaseParamsAndAssertions(testCase, assertions, params);
        final var responseBody = sendListOrdersRequest(params);
        final var orders = objectMapper.readValue(responseBody, new TypeReference<List<Order>>() {});
        assertions.forEach(assertion -> assertion.accept(orders));
    }

    private @NotNull String sendAddOrderRequest() throws Exception {
        return mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADD_ORDER_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private @NotNull String sendListOrdersRequest(LinkedMultiValueMap<String, String> params) throws Exception {
        return mockMvc.perform(get("/api/v1/orders")
                        .params(params))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private @Nullable Boolean orderExistsById(UUID id) {
        return jdbcTemplate.queryForObject("SELECT count(*) = 1 FROM orders WHERE id = ?", boolean.class, id);
    }

    private static void fillTestCaseParamsAndAssertions(ListOrdersTestCase testCase, ArrayList<Consumer<List<Order>>> assertions, LinkedMultiValueMap<String, String> params) {
        assertions.add(orders -> assertThat(orders).size().isEqualTo(testCase.expectedResultSize()));
        Optional.ofNullable(testCase.customer()).ifPresent(customer -> {
            params.add("customer", customer);
            assertions.add(orders -> assertThat(orders).allMatch(order -> customer.equals(order.getCustomer())));
        });
        Optional.ofNullable(testCase.product()).ifPresent(product -> {
            params.add("product", product);
            assertions.add(orders -> assertThat(orders).allMatch(order -> product.equals(order.getProduct())));
        });
    }

    private static void assertAddOrderRequestDtoMatchesToOrder(AddOrderRequestDto givenRequestDto, OrderDto order) {
        assertThat(givenRequestDto.orderTime()).isEqualTo(order.orderTime());
        assertThat(givenRequestDto.customer()).isEqualTo(order.customer());
        assertThat(givenRequestDto.product()).isEqualTo(order.product());
        assertThat(givenRequestDto.quantity()).isEqualTo(order.quantity());
        assertThat(givenRequestDto.pricePerUnit()).isEqualTo(order.pricePerUnit());
        assertThat(givenRequestDto.totalPrice()).isEqualTo(order.totalPrice());
    }

    private static Stream<ListOrdersTestCase> listOrdersTestCases() {
        return Stream.of(
                ListOrdersTestCase.builder()
                        .caseDescription("List orders w/o parameters")
                        .expectedResultSize(5)
                        .build(),
                ListOrdersTestCase.builder()
                        .caseDescription("List orders by customer")
                        .customer("Fred")
                        .expectedResultSize(3)
                        .build(),
                ListOrdersTestCase.builder()
                        .caseDescription("List orders by product")
                        .product("Screwdriver")
                        .expectedResultSize(2)
                        .build(),
                ListOrdersTestCase.builder()
                        .caseDescription("List orders by customer and product")
                        .customer("Fred")
                        .product("Screwdriver")
                        .expectedResultSize(1)
                        .build()
        );
    }

    public record ListOrdersTestCase(
            String caseDescription,
            String customer,
            String product,
            int expectedResultSize
    ) {

        @Builder
        public ListOrdersTestCase {
        }

        @Override
        public @NotNull String toString() {
            return caseDescription;
        }
    }
}