package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.Order;
import com.example.restaurant_pos.model.OrderItem;
import com.example.restaurant_pos.model.Product;
import com.example.restaurant_pos.model.request.OrderItemRequestDTO;
import com.example.restaurant_pos.model.request.OrderRequestDTO;
import com.example.restaurant_pos.repository.OrderRepository;
import com.example.restaurant_pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    public List<Order> getOrder(Integer id){
        List<Order> orders = new ArrayList<>();
        if(id == null) {
            return orderRepository.findAll();
        } else {
            orders.add(orderRepository.findById(id).get());
            return orders;
        }
    }

    public List<Order> getOrdersByDay(LocalDate date){
        LocalDateTime startOfDay = date.atStartOfDay();  // 2025-02-10T00:00:00
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 2025-02-10T23:59:59
        return orderRepository.findByOrderDateBetween(startOfDay, endOfDay);
    }

    public List<Order> getOrdersbyMonth(LocalDate parsedDate) {
        // Extract year and month from the provided date
        int year = parsedDate.getYear();
        int month = parsedDate.getMonthValue();

        // Define start and end of the month
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.withDayOfMonth(parsedDate.lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        // Fetch orders within the given month range
        return orderRepository.findByOrderDateBetween(startOfMonth, endOfMonth);
    }

    public String getDailyIncome(LocalDate parsedDate){
        List<Order> orders = getOrdersByDay(parsedDate);
        double total = 0;
        for (Order orderItem : orders) {
            total += orderItem.getTotalPrice();
        }
        return "Todays Income Is: " + total;
    }

    public String getMonthlyIncome(LocalDate parsedDate){
        List<Order> orders = getOrdersbyMonth(parsedDate);
        double total = 0;
        for (Order orderItem : orders) {
            total += orderItem.getTotalPrice();
        }
        return "This Months Income Is: " + total;
    }

    public void deleteOrder(Integer id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderRepository.delete(order);
    }


    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        double totalPrice = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO itemDTO : orderRequestDTO.getOrderItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            double itemTotal = product.getPrice() * itemDTO.getQuantity();
            totalPrice += itemTotal;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }
        order.setOrderDate(orderRequestDTO.getOrderDate());
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }
}
