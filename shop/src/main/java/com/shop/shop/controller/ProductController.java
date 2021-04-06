package com.shop.shop.controller;

import com.shop.shop.entity.CartItem;
import com.shop.shop.entity.Order;
import com.shop.shop.entity.PhotoProduct;
import com.shop.shop.entity.Product;
import com.shop.shop.repository.*;
import com.shop.shop.request.ProductRequest;
import com.shop.shop.service.OrderService;
import com.shop.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import response.OrderDTO;
import response.ProductDTO;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    PhotoProductRepository photoProductRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/product_view")
    public String createProduct(Model model, @Param("search") String search, Pageable pageable) {
        model.addAttribute("search", search);
        Page<ProductDTO> productDTO = productService.search(search,pageable);
        model.addAttribute("products", productDTO);
        return "admin/product_view";
    }
    @GetMapping("/add_product")
    public String add(Model model) {
        ProductRequest productRequest = new ProductRequest();
        model.addAttribute("productRequest", productRequest);
        model.addAttribute("category", categoryRepository.findAll());
        return "admin/add_product";
    }
    private String getFileURL(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();
    }
    @GetMapping("/edit_product/{productId}")
    public String get(Model model,@PathVariable("productId") Integer productId) {
        Optional<Product> product=productRepository.findById(productId);
        PhotoProduct productList=photoProductRepository.findAllByProductId(productId);
        ProductRequest productRequest = ProductRequest.builder()
                .id(product.get().getId())
                .name(product.get().getName())
                .metaTitle(product.get().getMetaTitle())
                .summary(product.get().getSummary())
                .type(product.get().getType())
                .price(product.get().getPrice())
                .discount(product.get().getDiscount())
                .quantity(product.get().getQuantity())
                .content(product.get().getContent())
                .photo(getFileURL(productList.getFileName()))
                .categoryId(product.get().getCategory().getId())
                .build();
        productRequest.setId(productId);
        model.addAttribute("productRequest", productRequest);
        model.addAttribute("category", categoryRepository.findAll());
        return "/admin/edit_product";
    }
    @PostMapping("/edit_product")
    public String edit(ProductRequest productRequest) {
        productService.edit(productRequest,productRequest.getId());
        return "redirect:/admin/product_view";
    }

    @PostMapping("/add_product")
    public String createProduct(ProductRequest productRequest) {
        ProductDTO productDTO=productService.create(productRequest);
        return "redirect:/admin/product_view";
    }

    @GetMapping("/order")
    public String order(Model model, Pageable pageable) {
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
        return "admin/order";
    }

    @GetMapping(value = {"/change-status/{orderId}"})
    String updateQuantity(@PathVariable("orderId") Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.get().getStatus().equals(0)) {
            order.get().setStatus(1);
        }
        if (order.get().getStatus().equals(1)) {
            order.get().setStatus(2);
        }
        if (order.get().getStatus().equals(2)) {
            order.get().setStatus(3);
        }
        orderRepository.save(order.get());
        return "redirect:/admin/order";
    }
    @GetMapping(value = "/Product_Delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer productId, Model model) {
        PhotoProduct photoProduct=photoProductRepository.findAllByProductId(productId);
        if(photoProduct!=null){
            photoProductRepository.deleteById(photoProduct.getId());
        }
        List<CartItem> cartItemList=cartItemRepository.findAllByProductId(productId);
        for (CartItem item: cartItemList) {
            cartItemRepository.deleteById(item.getId());
        }
        productRepository.deleteById(productId);
        return "redirect:/admin/product_view";
    }
}
