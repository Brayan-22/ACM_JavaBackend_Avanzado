package com.acm.orderservice.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING,
    CANCELLED,
    SHIPPED
}
