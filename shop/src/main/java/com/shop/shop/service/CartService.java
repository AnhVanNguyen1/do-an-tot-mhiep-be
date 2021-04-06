package com.shop.shop.service;

import com.shop.shop.request.AddCartRequest;
import response.CartDTO;

public interface CartService {
    CartDTO addToCart(AddCartRequest addCartRequest);
}
