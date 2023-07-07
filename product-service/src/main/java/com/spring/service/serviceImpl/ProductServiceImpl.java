package com.spring.service.serviceImpl;

import com.spring.dto.ProductDTO;
import com.spring.entity.Product;
import com.spring.repository.ProductRepository;
import com.spring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice()).build();
        return ProductDTO.getEntity(repo.save(product));
    }

    @Override
    public List<ProductDTO> getAll() {
        return repo.findAll().stream().map(entity ->
                new ProductDTO(entity.getId(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getPrice())).collect(Collectors.toList());
    }
}
