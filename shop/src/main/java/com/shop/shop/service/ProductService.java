package com.shop.shop.service;

import com.shop.shop.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import response.ProductDTO;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> search(String search, Pageable pageable);
    ProductDTO create(ProductRequest productRequest);
    ProductDTO edit(ProductRequest productRequest, Integer productId);
    Page<ProductDTO> searchByCategory(Pageable pageable, Integer categoryId);
    ProductDTO detail(Integer productId);
    Page<ProductDTO> search2(String search, Integer categoryId, Pageable pageable);
}
