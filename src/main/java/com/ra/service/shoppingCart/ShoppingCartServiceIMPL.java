package com.ra.service.shoppingCart;

import com.ra.model.dto.request.ShoppingCartRequest;
import com.ra.model.dto.response.ShoppingCartResponse;
import com.ra.model.entity.Product;
import com.ra.model.entity.Shopping_Cart;
import com.ra.model.entity.User;
import com.ra.repository.ShoppingCartRepository;
import com.ra.security.user_principal.UserPrinciple;
import com.ra.service.UserAndRole.UserService;
import com.ra.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartServiceIMPL implements ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    private User userLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            return userService.findById(userPrinciple.getUser().getId());
        } else {
            logger.error("User - ShoppingCartController - User id is not found.");
            return null;
        }
    }

    private final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceIMPL.class);

    @Override
    public Page<ShoppingCartResponse> getAll(Pageable pageable) {
        Page<Shopping_Cart> shoppingCarts = shoppingCartRepository.findAllByUser(userLogin(), pageable);
        return shoppingCarts.map(this::convertShoppingCartToShoppingCartResponse);
    }

    @Override
    public Shopping_Cart save(Shopping_Cart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteOneProduct(Long id) {
        shoppingCartRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void deleteAllProduct() {
        shoppingCartRepository.deleteByUser(userLogin());
    }

    @Override
    public Shopping_Cart findById(Long Id) {
        return shoppingCartRepository.findShopping_CartByById(Id, Objects.requireNonNull(userLogin()).getId());
    }

    public Shopping_Cart convertShoppingCartRequestToShoppingCart(ShoppingCartRequest shoppingCartRequest) {
        Product product = productService.findById(shoppingCartRequest.getProductId());
        if (product==null){
            throw new RuntimeException();
        }
        return Shopping_Cart.builder().
                orderQuantity(shoppingCartRequest.getOrderQuantity()).
                product(product).
                user(userLogin()).
                build();
    }

    public ShoppingCartResponse convertShoppingCartToShoppingCartResponse(Shopping_Cart shoppingCart) {
        return ShoppingCartResponse.builder()
                .productName(shoppingCart.getProduct().getProductName())
                .unitPrice(shoppingCart.getProduct().getUnitPrice())
                .orderQuantity(shoppingCart.getOrderQuantity())
                .build();
    }
}
