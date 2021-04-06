package com.shop.shop.service.Impl;

import com.shop.shop.common.ModelMapperUtils;
import com.shop.shop.entity.*;
import com.shop.shop.repository.*;
import com.shop.shop.request.OrderRequest;
import com.shop.shop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import response.OrderDTO;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final AccountRepository accountRepository;

    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, AccountRepository accountRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.accountRepository = accountRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public OrderDTO createOrderByUser(OrderRequest orderRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User account = accountRepository.findByEmail(authentication.getName());
            Cart cart = cartRepository.findByAccountId(account.getId());
            List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cart.getId());
            double subTotal=0;
            for (CartItem item : cartItemList) {
                subTotal+=item.getProduct().getPrice()*item.getQuantity()*(1-item.getProduct().getDiscount()/100);
            }
            Order order = ModelMapperUtils.map(orderRequest, Order.class);
            order.setAccount(account);
            order.setShipping(orderRequest.getShipping());
            order.setStatus(Order.Status.STATUS_WAIT_CONFIRM.getValue());
            order.setCreateAt(new Date());
            order.setSubTotal(subTotal);
            order.setGrandTotal(subTotal+orderRequest.getShipping());
            Order order1 = orderRepository.save(order);
            System.out.println(order1.toString());
            for (CartItem item : cartItemList) {
                OrderItem orderItem = OrderItem.builder()
                        .order(order1)
                        .product(item.getProduct())
                        .quantity(item.getQuantity())
                        .createAt(new Date())
                        .discount(item.getProduct().getDiscount())
                        .price(item.getProduct().getPrice())
                        .build();
                cartItemRepository.deleteById(item.getId());
                orderItemRepository.save(orderItem);
            }
            log.info("function : create order success");
            return null;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Create product fail");
        }
    }

    @Override
    public Page<OrderDTO> getAllByStatus(Integer status, Pageable pageable) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User account = accountRepository.findByEmail(authentication.getName());
            Page<Order> orderPage=orderRepository.findAllByStatusAndAccountId(status,account.getId(),pageable);
            Page<OrderDTO> orderDTOS = orderPage.map(order -> ModelMapperUtils.map(order, OrderDTO.class));
            log.info("getList order success");
            return orderDTOS;
        } catch (Exception e) {
            log.error("getList order fail", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
