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

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<ProductResponse> getAllPermitAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::convertProductToProductResponse);
    }

    @Override
    public List<ProductResponse> findByKeyWord(String keyWord) {
        List<Product> products = productRepository.findByProductNameOrDescription(keyWord);
        return products.stream()
                .map(this::convertProductToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findByIdPermitAll(Long id) {
        Product product = findById(id);
        return convertProductToProductResponse(product);
    }

    @Override
    public List<ProductResponse> findByCategoryId(Long id) {
        Category category = categoryService.findById(id);
        List<Product> products = productRepository.findAllByCategory(category);
        return products.stream()
                .map(this::convertProductToProductResponse)
                .collect(Collectors.toList());
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
                .categoryName(category.getCategoryName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .image(product.getImage())
                .build();
    }
}
