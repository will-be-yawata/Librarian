package com.zjq.library_01.controller;

import com.zjq.library_01.domain.Category;
import com.zjq.library_01.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/category/getAll")
    @ResponseBody
    public List<Category> getAll(){
        return categoryService.getAll();
    }
}
