package com.shop.shop.controller;

import com.shop.shop.entity.User;
import com.shop.shop.entity.Cart;
import com.shop.shop.entity.CartItem;
import com.shop.shop.entity.Category;
import com.shop.shop.repository.*;
import com.shop.shop.service.AccountService;
import com.shop.shop.service.CartItemService;
import com.shop.shop.service.CartService;
import com.shop.shop.service.Impl.PhotoProductServiceImpl;
import com.shop.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import response.AccountDTO;
import response.ProductDTO;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    AccountService accountService;

    @Autowired
    PhotoProductServiceImpl service;

    @GetMapping(value = {"/home"})
    String index(Model model, @Param("search") String search,@PageableDefault(size = 8, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("search", search);
        Page<ProductDTO> productDTO = productService.search(search,pageable);
        model.addAttribute("products", productDTO);
        return "index";
    }
    @GetMapping(value = {"/product-grid"})
    String listProduct(Model model,@Param("search") String search,@PageableDefault(size = 9, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable,@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        List<Category> categoryList=categoryRepository.findAll();
        model.addAttribute("categories", categoryList);
        model.addAttribute("search", search);
        Page<ProductDTO> productDTO = productService.search2(search,categoryId,pageable);
        model.addAttribute("products", productDTO);
        return "product-grid";
    }
    @GetMapping(value = {"/product-detail/{productId}"})
    String getProductById(Model model,@PathVariable("productId") Integer productId,@Param("search") String search, Pageable pageable) {
        ProductDTO productDTO = productService.detail(productId);
        model.addAttribute("productDTO", productDTO);
        return "product-details";
    }
    @PostMapping(value = {"/cartItem-quantity"})
    String updateQuantity(@RequestParam("quantity") Integer quantity,
                          @RequestParam("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User account = accountRepository.findByEmail(authentication.getName());
            Cart cart = cartRepository.findByAccountId(account.getId());
            CartItem cartItem=cartItemRepository.findByCartIdAndProductId(cart.getId(),id);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
        return "redirect:cart/cart-view";
    }
    @GetMapping("/profile")
    public String profile(Model model) {
        AccountDTO accountDTO=accountService.getOne();
        model.addAttribute("accountDTO",accountDTO);
        return "/profile";
    }
    private String getFileURL(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();
    }
    @PostMapping("/profile")
    public String edit(Model model,AccountDTO accountDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User account = accountRepository.findByEmail(authentication.getName());
        service.storeFile(accountDTO.getFile());
        User account1= User.builder()
                .id(accountDTO.getId())
                .firstName(accountDTO.getFirstName())
                .lastName(accountDTO.getLastName())
                .profile(accountDTO.getFile().getName())
                .email(accountDTO.getEmail())
                .mobile(accountDTO.getMobile())
                .password(account.getPassword())
                .userRole(account.getUserRole())
                .registeredAt(account.getRegisteredAt())
                .build();
        accountRepository.save(account1);
        return "/profile";
    }
}

