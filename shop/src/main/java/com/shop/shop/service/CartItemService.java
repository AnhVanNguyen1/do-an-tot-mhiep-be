package com.shop.shop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import response.CartItemDTO;

public interface CartItemService {
    Page<CartItemDTO> getAllProductByCart(Pageable pageable);

}
