package com.odr.order_service.controller;

import com.odr.order_service.entity.Order;
import com.odr.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // @Autowired
    // private RestClient restClient;

    @Autowired
    private RestClient.Builder restClientBuilder;


    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // Validate user
    try {
        //restTemplate.getForObject("http://user-service/users/" + order.getUserId(), Object.class);
        
         //restClient.get().uri("http://user-service/users/" + order.getUserId()).retrieve().body(Object.class);

         restClientBuilder.build().get().uri("http://user-service/users/" + order.getUserId())
                .retrieve().body(Object.class);

    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("User not found");
    }

    // Validate product
    try {
        //restTemplate.getForObject("http://product-service/products/" + order.getProductId(), Object.class);
        //restClient.get().uri("http://product-service/products/" + order.getProductId()).retrieve().body(Object.class);
        restClientBuilder.build().get().uri("http://product-service/products/" + order.getUserId())
                .retrieve().body(Object.class);

    } catch (Exception e) {
        throw new RuntimeException("Product not found");
    }

    return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setProductId(orderDetails.getProductId());
        order.setQuantity(orderDetails.getQuantity());
        order.setUserId(orderDetails.getUserId());
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}
