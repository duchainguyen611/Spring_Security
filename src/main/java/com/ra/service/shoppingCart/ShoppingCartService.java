package com.ra.service.shoppingCart;

import com.ra.model.entity.Shopping_Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    Page<Shopping_Cart> getAll(Pageable pageable);

    Shopping_Cart save(Shopping_Cart shoppingCart);

    Shopping_Cart updateQuantity(Long id);

    void deleteOneProduct(Long id);

    void deleteAllProduct();
}
