package com.spring.service.serviceImpl;

import com.spring.InventoryDTO;
import com.spring.repository.InventoryRepository;
import com.spring.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<InventoryDTO> checkAvailability(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
                InventoryDTO.builder().skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0) // populate isInStock after verify the quantity is greater then 0
                        .build()
        ).toList();
    }
}
