package com.example.restaurant_pos.model;

public enum OrderStatus {
    PENDING,     // Order just placed
    PREPARING,   // Kitchen is making the order
    READY,       // Order is ready for pickup or serving
    COMPLETED,   // Order is served and finished
    CANCELLED    // Order was cancelled
}
