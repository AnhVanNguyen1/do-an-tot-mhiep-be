package com.shop.shop.service.Impl;

import com.shop.shop.entity.Category;
import com.shop.shop.repository.CategoryRepository;
import com.shop.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
