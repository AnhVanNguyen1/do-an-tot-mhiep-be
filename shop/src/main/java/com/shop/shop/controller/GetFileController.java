package com.shop.shop.controller;

import com.shop.shop.request.AddCartRequest;
import com.shop.shop.service.CartItemService;
import com.shop.shop.service.CartService;
import com.shop.shop.service.Impl.PhotoProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.CartDTO;

/**
 * @author Lê Thị Thúy
 */
@RestController
@RequestMapping("/api/")
public class GetFileController {
    private final PhotoProductServiceImpl photoProductService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    public GetFileController(PhotoProductServiceImpl photoProductService) {
        this.photoProductService = photoProductService;
    }

    @GetMapping("download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {
        Resource resource = photoProductService.loadFileAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @GetMapping("cart/add/{productId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable("productId") Integer productId) {
        AddCartRequest addCartRequest = new AddCartRequest();
        addCartRequest.setProductId(productId);
        return ResponseEntity.ok(cartService.addToCart(addCartRequest));
    }
    @GetMapping(value = "/add/{productId}")
    public ResponseEntity<CartDTO> addCart(@PathVariable("productId") Integer productId) {
        AddCartRequest addCartRequest = new AddCartRequest();
        addCartRequest.setProductId(productId);
        cartService.addToCart(addCartRequest);
        return ResponseEntity.ok(cartService.addToCart(addCartRequest));
    }
}
