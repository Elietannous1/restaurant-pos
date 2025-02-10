package com.example.restaurant_pos.model.request;

import com.example.restaurant_pos.model.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRequestDTO {

    private LocalDateTime orderDate;
    private List<OrderItemRequestDTO> orderItems;

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItemRequestDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequestDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
