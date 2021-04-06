package com.shop.shop.service;

import com.shop.shop.entity.User;
import com.shop.shop.request.UserCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import response.AccountDTO;

public interface AccountService {

    AccountDTO registerUser(UserCreateRequest userCreateRequest);

    Page<AccountDTO> getAll(String search,Pageable pageable);

    AccountDTO getOne();

    AccountDTO edit(UserCreateRequest userCreateRequest);

    AccountDTO updateResetPasswordToken(String resetToken,String email);

    void updatePasswordToken(User user, String newPassword);
}
