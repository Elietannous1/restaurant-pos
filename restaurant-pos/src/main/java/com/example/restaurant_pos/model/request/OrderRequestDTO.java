package com.example.restaurant_pos.model.request;


import java.time.LocalDateTime;
import java.util.List;

public class OrderRequestDTO {

    private LocalDateTime orderDate;
    private List<OrderItemRequestDTO> orderItems;
    private String orderStatus;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
