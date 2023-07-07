package com.spring.service;



import com.spring.InventoryDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryDTO> checkAvailability(List<String> skuCode);
}
