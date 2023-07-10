package com.spring.controller;

import com.spring.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<?> isInStock(@RequestParam List<String> skuCode){
        return ResponseEntity.ok(inventoryService.checkAvailability(skuCode));
    }
}
