package com.example.restaurant_pos.service;

import com.example.restaurant_pos.model.*;
import com.example.restaurant_pos.model.request.OrderItemRequestDTO;
import com.example.restaurant_pos.model.request.OrderRequestDTO;
import com.example.restaurant_pos.model.response.OrderItemResponseDTO;
import com.example.restaurant_pos.model.response.OrderResponseDTO;
import com.example.restaurant_pos.repository.OrderRepository;
import com.example.restaurant_pos.repository.ProductRepository;
import com.example.restaurant_pos.repository.ProductSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService  productService;

    @Autowired
    ProductSalesRepository  productSalesRepository;

    public List<OrderResponseDTO> getOrder(Integer id) {
        List<Order> orders = new ArrayList<>();
        if (id == null) {
            orders = orderRepository.findAll();
        } else {
            Optional<Order> optionalOrder = orderRepository.findById(id);
            if (optionalOrder.isPresent()) {
                orders.add(optionalOrder.get());
            }
        }
        // Convert each Order entity to a DTO enriched with product details
        List<OrderResponseDTO> responseList = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDTO orderDTO = new OrderResponseDTO();
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setOrderStatus(String.valueOf(order.getOrderStatus()));

            List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();
            for (OrderItem item : order.getOrderItems()) {
                OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                itemDTO.setOrderItemId(item.getOrderItemId());
                itemDTO.setProductId(item.getProduct().getId());
                itemDTO.setQuantity(item.getQuantity());
                // Call productService to get product details
                Product product = productService.getProductById(item.getProduct().getId());
                if (product != null) {
                    itemDTO.setProductName(product.getProductName());
                    itemDTO.setPrice(product.getPrice());
                }
                itemDTOs.add(itemDTO);
            }
            orderDTO.setOrderItems(itemDTOs);
            responseList.add(orderDTO);
        }
        return responseList;
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

    public Order updateOrder(Integer id, OrderStatus status, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Cannot update a completed order.");
        }

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot update a cancelled order.");
        }

        // If order items are provided in the request, update them along with total price and order date.
        if (orderRequestDTO.getOrderItems() != null && !orderRequestDTO.getOrderItems().isEmpty()) {
            List<OrderItem> updatedOrderItems = new ArrayList<>();
            double totalPrice = 0.0;

            for (OrderItemRequestDTO itemDTO : orderRequestDTO.getOrderItems()) {
                Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                double itemTotal = product.getPrice() * itemDTO.getQuantity();
                totalPrice += itemTotal;

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setOrder(order);

                updatedOrderItems.add(orderItem);
            }
            order.setOrderItems(updatedOrderItems);
            order.setTotalPrice(totalPrice);
            // Optionally update the order date if provided
            if(orderRequestDTO.getOrderDate() != null){
                order.setOrderDate(orderRequestDTO.getOrderDate());
            }
        }

        // Always update the status, regardless of whether order items were provided.
        order.setOrderStatus(status);

        if (status == OrderStatus.COMPLETED) {
            updateProductSales(order);
        }

        return orderRepository.save(order);
    }




    public Order createOrder(OrderStatus status,OrderRequestDTO orderRequestDTO) {
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
        order.setOrderStatus(status);

        if (status == OrderStatus.COMPLETED) {
            updateProductSales(order);
        }

        return orderRepository.save(order);
    }

    private void updateProductSales(Order order) {
        LocalDate today = LocalDate.now();

        for (OrderItem item : order.getOrderItems()) {
            productSalesRepository.findByProductIdAndSaleDate(item.getProduct().getId(), today)
                    .ifPresentOrElse(
                            productSales -> {
                                productSales.setQuantitySold(productSales.getQuantitySold() + item.getQuantity());
                                productSalesRepository.save(productSales);
                            },
                            () -> {
                                // Using a constructor instead of builder
                                ProductSales newSales = new ProductSales(item.getProduct(), today, item.getQuantity());
                                productSalesRepository.save(newSales);
                            }
                    );
        }
    }

}
