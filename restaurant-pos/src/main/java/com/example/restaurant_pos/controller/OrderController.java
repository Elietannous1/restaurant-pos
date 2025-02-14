package com.example.restaurant_pos.controller;

import com.example.restaurant_pos.model.Order;
import com.example.restaurant_pos.model.OrderStatus;
import com.example.restaurant_pos.model.request.OrderRequestDTO;
import com.example.restaurant_pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// TODO: Add status to order entity and filtering(if there is a lot of pending orders.)
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;


    @GetMapping("")
    @ResponseBody
    public List<Order> getAllOrders(@RequestParam(required = false) Integer id){
        return orderService.getOrder(id);
    }

    @PostMapping("/create")
    @ResponseBody
    public Order createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @GetMapping("/date/day")
    @ResponseBody
    public List<Order> getOrdersForDay(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return orderService.getOrdersByDay(parsedDate);
    }

    @GetMapping("/date/month")
    @ResponseBody
    public List<Order> getOrdersForMonth(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return orderService.getOrdersbyMonth(parsedDate);
    }

    @GetMapping("/income")
    @ResponseBody
    public String getIncome(@RequestParam String date, @RequestParam String period) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        if(period.equalsIgnoreCase("day")){
            return orderService.getDailyIncome(parsedDate);
        } else if (period.equalsIgnoreCase("month")){
            return orderService.getMonthlyIncome(parsedDate);
        } else {
            throw new IllegalArgumentException("Invalid period. Please specify 'day' or 'month'.");
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteOrder(@RequestParam Integer id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order with ID " + id + " has been deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
    }

    @PutMapping("/update/status")
    @ResponseBody
    public ResponseEntity<Order> updateOrderStatus(@RequestParam Integer id, @RequestParam String inputStatus) {
        OrderStatus status = validateOrderStatus(inputStatus.toUpperCase());
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }

}
