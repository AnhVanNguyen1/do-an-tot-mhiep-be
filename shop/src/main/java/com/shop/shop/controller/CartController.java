package com.shop.shop.controller;

import com.shop.shop.repository.CartItemRepository;
import com.shop.shop.request.AddCartRequest;
import com.shop.shop.service.CartItemService;
import com.shop.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import response.CartItemDTO;

/**
 * @author Lê Thị Thúy
 * @created 3/13/2021
 * @project shop
 */
@Controller
@RequestMapping(value = "/cart")
public  class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartItemService cartItemService;
    @PostMapping(value = "/add/{productId}")
    public String getCreate(@PathVariable("productId") Integer productId) {
        AddCartRequest addCartRequest = new AddCartRequest();
        addCartRequest.setProductId(productId);
        cartService.addToCart(addCartRequest);
        return "redirect:/cart/cart-view";
    }
    @GetMapping(value = "/cart-view")
    public String getAllByAccount(Model model,@PageableDefault(size = 8, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CartItemDTO> cartItemDTOS =cartItemService.getAllProductByCart(pageable);
        model.addAttribute("cartItemDTOS",cartItemDTOS);
        return "cart";
    }
    @GetMapping(value = "/menu")
    public String menu(Model model,@PageableDefault(size = 8, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CartItemDTO> cartItemDTOS =cartItemService.getAllProductByCart(pageable);
        Integer sumSl=0;
        for (CartItemDTO item:cartItemDTOS) {
            sumSl+=item.getQuantity();
        }
        model.addAttribute("sumSl",sumSl);
        model.addAttribute("cartItemDTOS",cartItemDTOS);
        return "fragments/menu";
    }
    @GetMapping("/remove-cartItem/{cartItemId}")
    public String viewRemove(@PathVariable("cartItemId") Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return "redirect:/cart/cart-view";
    }
}
