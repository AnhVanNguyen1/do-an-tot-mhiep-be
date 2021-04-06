package com.shop.shop.service;

import com.shop.shop.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import response.OrderDTO;

public interface OrderService {
    OrderDTO createOrderByUser(OrderRequest orderRequest);

    Page<OrderDTO> getAllByStatus(Integer status, Pageable pageable);

}
