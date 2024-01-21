package com.ra.controller.User;

import com.ra.model.dto.request.ShoppingCartRequest;
import com.ra.model.dto.response.CheckOut;
import com.ra.model.dto.response.ShoppingCartResponse;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/shopping-cart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<ShoppingCartResponse> shoppingCarts = shoppingCartService.getAll(pageable);
        return new ResponseEntity<>(shoppingCarts, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ShoppingCartResponse> addProduct(@RequestBody ShoppingCartRequest shoppingCartRequest){
        Shopping_Cart shoppingCart = shoppingCartService.convertShoppingCartRequestToShoppingCart(shoppingCartRequest);
        Shopping_Cart shoppingCartNew = shoppingCartService.save(shoppingCart);
        return new ResponseEntity<>(shoppingCartService.convertShoppingCartToShoppingCartResponse(shoppingCartNew),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCartResponse> updateQuantity(@PathVariable Integer id,@RequestBody ShoppingCartRequest shoppingCartRequest){
        Shopping_Cart shoppingCartSearch = shoppingCartService.findById(id);
        if (shoppingCartSearch==null){
            throw new RuntimeException();
        }
        shoppingCartSearch.setOrderQuantity(shoppingCartRequest.getOrderQuantity());
        Shopping_Cart shoppingCartUpdate = shoppingCartService.save(shoppingCartSearch);
        return new ResponseEntity<>(shoppingCartService.convertShoppingCartToShoppingCartResponse(shoppingCartUpdate),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        shoppingCartService.deleteOneProduct(id);
        return new ResponseEntity<>("Delete success!",HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAll(){
        shoppingCartService.deleteAllProduct();
        return new ResponseEntity<>("Delete success!",HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(@RequestBody CheckOut checkOut){
        shoppingCartService.checkOut(checkOut.getAddressId());
        return new ResponseEntity<>("Check Out success!",HttpStatus.OK);
    }
}
