package com.ra.service.product;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAll(Pageable pageable);
    Product findById(Long id);
    Product save(Product product);
    void delete(Long id);
    Product convertProductRequestToProduct(ProductRequest productRequest);

}
