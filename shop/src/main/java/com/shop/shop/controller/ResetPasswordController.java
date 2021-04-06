package com.shop.shop.controller;

import com.shop.shop.entity.User;
import com.shop.shop.repository.AccountRepository;
import com.shop.shop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;

/**
 * @author Lê Thị Thúy
 * @created 4/4/2021
 * @project shop
 */
@Controller
public class ResetPasswordController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = accountRepository.findByResetPassword(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }
    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User user = accountRepository.findByResetPassword(token);
        model.addAttribute("title", "Reset your password");
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return null;
        } else {
            accountService.updatePasswordToken(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "success";
    }
}
