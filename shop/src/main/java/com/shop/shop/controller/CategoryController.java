package com.shop.shop.controller;

import com.shop.shop.entity.Category;
import com.shop.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categoryList")
    public String viewCategory(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        return "admin/view_category";
    }
    @GetMapping(value = "/edit_category/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        Optional<Category> category=categoryRepository.findById(id);
        model.addAttribute("category",category );
        return "/admin/edit_category";
    }
    @PostMapping("/edit_category")
    public String editCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/categoryList";
    }
    @PostMapping("/add_category" )
    public String saveCategory(@ModelAttribute Category category) {
        if (category != null) {
            categoryRepository.save(category);
            return "redirect:/admin/categoryList";
        }
        return null;
    }
    //	add category
    @GetMapping(value = "/add_category")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "admin/add_category";
    }

}
