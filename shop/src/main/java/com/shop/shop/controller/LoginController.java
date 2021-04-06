package com.shop.shop.controller;

import com.shop.shop.request.UserCreateRequest;
import com.shop.shop.service.AccountService;
import com.shop.shop.service.Impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import response.AccountDTO;

@Controller
public class LoginController {
    @Autowired
    AccountService userService;

    @Autowired
    AccountServiceImpl accountService;
    @GetMapping("/api/login")
    private String login() {
        return "login";
    }
    @PostMapping("/register")
   private ResponseEntity<AccountDTO> create(@RequestBody UserCreateRequest userRequest) {
        return ResponseEntity.ok(userService.registerUser(userRequest));
    }
}
