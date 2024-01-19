package com.ra.controller.PermitAll;

import com.ra.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/categories")
public class PermitAllCategories {
    @Autowired
    private CategoryService categoryService;


}

