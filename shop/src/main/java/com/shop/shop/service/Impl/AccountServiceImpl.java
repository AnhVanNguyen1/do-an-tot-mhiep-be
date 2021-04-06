package com.shop.shop.service.Impl;

import com.shop.shop.common.ModelMapperUtils;
import com.shop.shop.entity.User;
import com.shop.shop.entity.Cart;
import com.shop.shop.repository.AccountRepository;
import com.shop.shop.repository.CartRepository;
import com.shop.shop.request.UserCreateRequest;
import com.shop.shop.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import response.AccountDTO;

import java.util.Date;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public AccountDTO registerUser(UserCreateRequest userRequest) {
        try{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User account = userRepository.findByEmail(userRequest.getEmail());
            if (account != null) {
                throw new ResponseStatusException(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
            User user = User.builder()
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .email(userRequest.getEmail())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .registeredAt(new Date())
                    .userRole("ROLE_EMPLOYEE")
                    .mobile(userRequest.getMobile())
                    .build();
            AccountDTO accountDTO = ModelMapperUtils.map(user, AccountDTO.class);
            User save = userRepository.save(user);
            Cart cart = Cart.builder()
                    .account(save)
                    .email(save.getEmail())
                    .firstName(save.getFirstName())
                    .lastName(save.getLastName())
                    .mobile(save.getMobile())
                    .createAt(new Date())
                    .build();
            Cart cartSave = cartRepository.save(cart);
            log.info("Function : Create a new user success");
            return accountDTO;
        }catch (Exception e){
            log.error("Function : Create a new user fail");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    private String getFileURL(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();
    }
    @Override
    public Page<AccountDTO> getAll(String search,Pageable pageable) {
        try {
            Page<User> accountPage;
            if (search != null) {
                accountPage=userRepository.search(search,pageable);
            }else {
                accountPage=userRepository.getAllBy(pageable);
            }
            Page<AccountDTO> accountDTOS = accountPage.map(account -> {
                AccountDTO accountDTO = ModelMapperUtils.map(account, AccountDTO.class);
                if(accountDTO.getPhoto()==null){
                    accountDTO.setPhoto(getFileURL("default.jpg"));
                }else {
                    accountDTO.setPhoto(getFileURL(accountDTO.getPhoto()));
                }
                return accountDTO;
            });
            log.info("getList account success");
            return accountDTOS;
        } catch (Exception e) {
            log.error("getList account fail", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public AccountDTO getOne() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User account = userRepository.findByEmail(authentication.getName());
        AccountDTO accountDTO = ModelMapperUtils.map(account, AccountDTO.class);
        if(accountDTO.getPhoto()==null){
            accountDTO.setPhoto(getFileURL("default.jpg"));
        }else {
            accountDTO.setPhoto(getFileURL(accountDTO.getPhoto()));
        }
        return accountDTO;
    }

    @Override
    public AccountDTO edit(UserCreateRequest userCreateRequest) {
        return null;
    }

    @Override
    public AccountDTO updateResetPasswordToken(String resetToken, String email) {
        User user=userRepository.findByEmail(email);
        if(user!=null){
            user.setResetPassword(resetToken);
            userRepository.save(user);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email does not exist");
        }
        return null;
    }
    public User get(String resetPasswordToken){
        return userRepository.findByResetPassword(resetPasswordToken);
    }
    @Override
    public void updatePasswordToken(User user,String newPassword){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String passWord= passwordEncoder.encode(newPassword);

        user.setPassword(passWord);
        userRepository.save(user);
    }
}
