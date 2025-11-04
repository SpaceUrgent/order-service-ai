package io.spaceurgent.order.service.ai.api;

import io.spaceurgent.order.service.ai.application.dto.AddOrderRequestDto;
import io.spaceurgent.order.service.ai.application.dto.MessageDto;
import io.spaceurgent.order.service.ai.application.dto.OrderDto;
import io.spaceurgent.order.service.ai.application.service.OrderAssistantService;
import io.spaceurgent.order.service.ai.application.service.OrderService;
import io.spaceurgent.order.service.ai.domain.repository.ListOrdersCriteria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderAssistantService orderAssistantService;

    @PostMapping
    public OrderDto addOrder(@RequestBody @Valid AddOrderRequestDto requestDto) {
        return orderService.addOrder(requestDto);
    }

    @GetMapping
    public List<OrderDto> listOrders(@RequestParam(name = "customer", required = false) String customer,
                                     @RequestParam(name = "product", required = false) String product) {
        final var criteria = ListOrdersCriteria.builder()
                .product(product)
                .customer(customer)
                .build();
        return orderService.listOrders(criteria);
    }

    @PostMapping("/assistant")
    public MessageDto askAssistant(@RequestBody @Valid MessageDto userMessage) {
        return orderAssistantService.ask(userMessage);
    }
}
