package com.spring.controller;

import com.spring.OrderDTO;
import com.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("place-order")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.placeOrder(orderDTO));
    }
}
