package com.shop.shop.controller;

import com.shop.shop.common.Utitily;
import com.shop.shop.entity.User;
import com.shop.shop.repository.AccountRepository;
import com.shop.shop.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import response.AccountDTO;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * @author Lê Thị Thúy
 * @created 3/22/2021
 * @project shop
 */
@RestController
@RequestMapping("/api/")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JavaMailSender mailSender;

    @GetMapping("/list-account")
    public ResponseEntity<Page<AccountDTO>> getAll(@Param("search") String search, Pageable pageable) throws Exception {
        Page<AccountDTO> accountDTOS = accountService.getAll(search, pageable);
        return ResponseEntity.ok(accountDTOS);
    }
    @GetMapping("/edit/{accountId}")
    public String edit(Model model, @PathVariable("accountId") Integer accountId) {
        Optional<User> account=accountRepository.findById(accountId);
        return "admin/acount";
    }
    @GetMapping(value = {"/user-role/{accountId}"})
    String updateQuantity(@PathVariable("accountId") Integer accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User account2=accountRepository.findByEmail(authentication.getName());
            Optional<User> account = accountRepository.findById(accountId);
            if(!account2.getId().equals(account.get().getId())){
                if(account.get().getUserRole().equals("ROLE_EMPLOYEE")){
                    account.get().setUserRole("ROLE_MANAGER");
                }else{
                    account.get().setUserRole("ROLE_EMPLOYEE");
                }
            }
            accountRepository.save(account.get());
        }
        return "redirect:/admin/account";
    }
    @PostMapping("/forgot_password")
    public ResponseEntity processForgotPassword(HttpServletRequest request, Model model, @Param("email") String email) {
        String token = RandomString.make(30);

        try {
            accountService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utitily.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);

        } catch (UnsupportedEncodingException | MessagingException e) {
        }

        return ResponseEntity.ok(null);
    }
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
