package io.spaceurgent.order.service.ai.domain.repository;

import lombok.Builder;

public record ListOrdersCriteria(String customer,
                                 String product) {

    @Builder
    public ListOrdersCriteria {
    }
}
