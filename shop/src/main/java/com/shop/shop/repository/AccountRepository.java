package com.shop.shop.repository;

import com.shop.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    @Query("SELECT p FROM User p WHERE p.firstName LIKE %?1%"
            + " OR p.email LIKE %?1%")
    Page<User> search(String search, Pageable pageable);

    Page<User> getAllBy(Pageable pageable);

    User findByResetPassword(String token);
}
