package com.spring.controller;


import com.spring.dto.ProductDTO;
import com.spring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO dto){
        return ResponseEntity.ok(service.createProduct(dto));
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct(){
        return ResponseEntity.ok(service.getAll());
    }
}
