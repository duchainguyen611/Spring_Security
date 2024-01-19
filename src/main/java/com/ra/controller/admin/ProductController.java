package com.ra.controller.admin;


import com.ra.model.dto.request.ProductRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;

import com.ra.service.category.CategoryService;
import com.ra.service.category.CategoryServiceIMPL;
import com.ra.service.product.ProductService;
import com.ra.service.product.ProductServiceIMPL;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/admin/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "productName", name = "sort")String sort) {
        Pageable pageable = PageRequest.of(page,limit,Sort.by(sort));
        Page<Product> products = productService.getAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Product> save(@RequestBody @Valid ProductRequest productRequest) {
        Product productValue = productService.convertProductRequestToProduct(productRequest);
        productValue.setCreatedAt(LocalDate.now());
        productValue.setSku(UUID.randomUUID().toString());
        Product productNew = productService.save(productValue);
        return new ResponseEntity<>(productNew, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody @Valid ProductRequest productRequest,@PathVariable Long id) {
        Product productValue = productService.convertProductRequestToProduct(productRequest);
        productValue.setUpdatedAt(LocalDate.now());
        productValue.setId(id);
        productValue.setSku(productService.findById(id).getSku());
        productValue.setCreatedAt(productService.findById(id).getCreatedAt());
        Product productUpdate = productService.save(productValue);
        return new ResponseEntity<>(productUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
