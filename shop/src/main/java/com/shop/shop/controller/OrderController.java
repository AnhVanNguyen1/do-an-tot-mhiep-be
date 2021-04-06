package com.shop.shop.controller;

import com.shop.shop.entity.Order;
import com.shop.shop.request.OrderRequest;
import com.shop.shop.service.CartItemService;
import com.shop.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import response.CartItemDTO;
import response.OrderDTO;

/**
 * @author Lê Thị Thúy
 * @created 3/18/2021
 * @project shop
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CartItemService cartItemService;

    @GetMapping("/checkout")
    public String add(Model model, Pageable pageable) {
        OrderRequest orderRequest = new OrderRequest();
        model.addAttribute("orderRequest", orderRequest);
        Page<CartItemDTO> cartItemDTOS = cartItemService.getAllProductByCart(pageable);
        model.addAttribute("cartItemDTOS", cartItemDTOS);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String createProduct(Model model, Pageable pageable, OrderRequest orderRequest) {
        OrderDTO orderDTO = orderService.createOrderByUser(orderRequest);
        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String history(Model model, Pageable pageable) {
        Page<OrderDTO> orderDTOS = orderService.getAllByStatus(Order.Status.STATUS_WAIT_CONFIRM.getValue(), pageable);
        model.addAttribute("orderDTOS", orderDTOS);
        Page<OrderDTO> orderDTOS1 = orderService.getAllByStatus(Order.Status.STATUS_WAIT_GOOD.getValue(), pageable);
        model.addAttribute("orderDTOS1", orderDTOS1);
        Page<OrderDTO> orderDTOS2 = orderService.getAllByStatus(Order.Status.STATUS_DELIVERY_PROGRESS.getValue(), pageable);
        model.addAttribute("orderDTOS2", orderDTOS2);
        Page<OrderDTO> orderDTOS3 = orderService.getAllByStatus(Order.Status.STATUS_DELIVERY.getValue(), pageable);
        model.addAttribute("orderDTOS3", orderDTOS3);
        Page<OrderDTO> orderDTOS4 = orderService.getAllByStatus(Order.Status.STATUS_CANCELLED.getValue(), pageable);
        model.addAttribute("orderDTOS4", orderDTOS4);
        return "history-order";
    }
}
