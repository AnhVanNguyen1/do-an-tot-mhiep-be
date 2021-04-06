package com.shop.shop.repository;

import com.shop.shop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import response.OrderDTO;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Page<Order>  findAllByStatusAndAccountId(Integer status, Integer accountId, Pageable pageable);
}
