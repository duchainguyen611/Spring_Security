package com.ra.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/admin/categories")
public class CategoryController {

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>("Danh sach dang nhap", HttpStatus.CREATED);
    }
}
