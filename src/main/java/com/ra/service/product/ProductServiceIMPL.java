package com.ra.service.product;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.dto.response.ProductResponse;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.repository.ProductRepository;
import com.ra.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceIMPL implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product convertProductRequestToProduct(ProductRequest productRequest){
        Category category = categoryService.findById(productRequest.getCategoryId());
        return Product.builder()
                .productName(productRequest.getProductName())
                .category(category)
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .image(productRequest.getImage())
                .build();
    }

    public ProductResponse convertProductToProductResponse(Product product){
        Category category = categoryService.findById(product.getCategory().getId());
        return ProductResponse.builder()
                .productName(product.getProductName())
                .categoryId(category.getId())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .build();
    }
}
