package com.shop.shop.service.Impl;

import com.shop.shop.common.ModelMapperUtils;
import com.shop.shop.entity.User;
import com.shop.shop.entity.Cart;
import com.shop.shop.entity.CartItem;
import com.shop.shop.entity.Product;
import com.shop.shop.repository.AccountRepository;
import com.shop.shop.repository.CartItemRepository;
import com.shop.shop.repository.CartRepository;
import com.shop.shop.repository.ProductRepository;
import com.shop.shop.request.AddCartRequest;
import com.shop.shop.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import response.CartDTO;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final AccountRepository accountRepository;

    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, AccountRepository accountRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartDTO addToCart(AddCartRequest addCartRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CartDTO cartDTO;
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                User account = accountRepository.findByEmail(authentication.getName());
                Cart cart = cartRepository.findByAccountId(account.getId());
                Optional<Product> product = productRepository.findById(addCartRequest.getProductId());
                CartItem cartItem=cartItemRepository.findByCartIdAndProductId(cart.getId(),product.get().getId());
                if(cartItem==null){
                     cartItem = CartItem.builder()
                            .cart(cart)
                            .product(product.get())
                             .quantity(1)
                            .createAt(new Date())
                            .build();
                }else{
                    cartItem.setQuantity(cartItem.getQuantity()+1);
                }
                cartItemRepository.save(cartItem);
                cartDTO = ModelMapperUtils.map(cart, CartDTO.class);
                log.info("function : create cartItem success");
                return cartDTO;
            }
            return null;
        } catch (Exception e) {
            log.info("Create cart item fail", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not logged in");
        }
    }
}
