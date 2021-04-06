package com.shop.shop.config;


import com.shop.shop.entity.User;
import com.shop.shop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = accountRepository.findByEmail(email);
        if (user == null) {
            throw new AuthenticationException("Email not exists"){};
        }
        return new CustomUserDetails(user);
    }
}
