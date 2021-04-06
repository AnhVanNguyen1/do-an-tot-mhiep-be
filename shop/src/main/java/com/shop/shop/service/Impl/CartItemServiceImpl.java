package com.shop.shop.service.Impl;

import com.shop.shop.common.ModelMapperUtils;
import com.shop.shop.entity.*;
import com.shop.shop.repository.*;
import com.shop.shop.service.CartItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import response.CartItemDTO;

/**
 * @author Lê Thị Thúy
 * @created 3/17/2021
 * @project shop
 */
@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    private final AccountRepository accountRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final PhotoProductRepository photoProductRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, AccountRepository accountRepository, CartRepository cartRepository, ProductRepository productRepository, PhotoProductRepository photoProductRepository) {
        this.cartItemRepository = cartItemRepository;
        this.accountRepository = accountRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.photoProductRepository = photoProductRepository;
    }

    private String getFileURL(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public Page<CartItemDTO> getAllProductByCart(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User account = accountRepository.findByEmail(authentication.getName());
            Cart cart = cartRepository.findByAccountId(account.getId());
            Page<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId(), pageable);
            Page<CartItemDTO> cartItemDTOS = cartItems.map(cartItem -> {
                CartItemDTO cartItemDTO = ModelMapperUtils.map(cartItem, CartItemDTO.class);
                PhotoProduct photoProduct = photoProductRepository.findAllByProductId(cartItemDTO.getProduct().getId());
                if (photoProduct == null) {
                    cartItemDTO.setPhoto(getFileURL("default.jpg"));
                } else {
                    cartItemDTO.setPhoto(getFileURL(photoProduct.getFileName()));
                }
                return cartItemDTO;
            });
            log.info("getList product success");
            return cartItemDTOS;
        }
        return null;
    }
}
