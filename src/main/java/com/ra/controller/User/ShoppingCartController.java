package com.ra.controller.User;

import com.ra.model.entity.Shopping_Cart;
import com.ra.model.entity.User;
import com.ra.service.UserAndRole.UserService;
import com.ra.service.UserAndRole.UserServiceIMPL;
import com.ra.service.shoppingCart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/shopping-cart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<Shopping_Cart> shoppingCarts = shoppingCartService.getAll(pageable);
        return new ResponseEntity<>(shoppingCarts, HttpStatus.OK);
    }
}
