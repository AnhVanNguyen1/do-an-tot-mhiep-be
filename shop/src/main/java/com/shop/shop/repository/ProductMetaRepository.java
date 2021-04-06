package com.shop.shop.repository;

import com.shop.shop.entity.ProductMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMetaRepository extends JpaRepository<ProductMeta,Integer> {
}
