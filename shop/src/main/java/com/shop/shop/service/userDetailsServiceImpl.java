package com.shop.shop.service;

import com.shop.shop.entity.User;
import com.shop.shop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class userDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User account = userRepository.findByEmail(email);
        System.out.println("Account= " + account);

        if (account == null) {
            throw new UsernameNotFoundException("User " //
                    + email + " was not found in the database");
        }

        // EMPLOYEE,MANAGER,..
        String role = account.getUserRole();

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

        // ROLE_EMPLOYEE, ROLE_MANAGER
        GrantedAuthority authority = new SimpleGrantedAuthority(role);

        grantList.add(authority);

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(account.getEmail(), //
                account.getPassword(), true, accountNonExpired, //
                credentialsNonExpired, accountNonLocked, grantList);
        System.out.println(userDetails);
        return userDetails;
    }
}
