package com.spring.service;



import com.spring.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO dto);

    List<ProductDTO> getAll();
}
