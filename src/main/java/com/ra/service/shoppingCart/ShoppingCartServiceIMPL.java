package com.ra.service.shoppingCart;

import com.ra.model.entity.Shopping_Cart;
import com.ra.model.entity.User;
import com.ra.repository.ShoppingCartRepository;
import com.ra.service.UserAndRole.UserService;
import com.ra.service.UserAndRole.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceIMPL implements ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    UserService userService;
    Long idUserLogin = UserServiceIMPL.idUserLogin;
    @Override
    public Page<Shopping_Cart> getAll(Pageable pageable) {
        User user = userService.findById(idUserLogin);
        return shoppingCartRepository.findAllByUser(user,pageable);
    }

    @Override
    public Shopping_Cart save(Shopping_Cart shoppingCart) {
        return null;
    }

    @Override
    public Shopping_Cart updateQuantity(Long id) {
        return null;
    }

    @Override
    public void deleteOneProduct(Long id) {

    }

    @Override
    public void deleteAllProduct() {

    }
}
