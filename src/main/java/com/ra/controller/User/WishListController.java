package com.ra.controller.User;

import com.ra.model.dto.request.WishListRequest;
import com.ra.model.dto.response.WishListResponse;
import com.ra.model.entity.Wish_List;
import com.ra.service.wishList.WishListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/wish-list")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @PostMapping("")
    public ResponseEntity<WishListResponse> addProductToWishList(@RequestBody @Valid WishListRequest wishListRequest) {
        Wish_List wish_list = wishListService.convertWishListRequestToWishListR(wishListRequest);
        Wish_List wish_listNew = wishListService.addProduct(wish_list);
        return new ResponseEntity<>(wishListService.convertWishListToWishListResponse(wish_listNew), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProductWishList(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<WishListResponse> wishListResponses = wishListService.getAll(pageable);
        return new ResponseEntity<>(wishListResponses,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductWishList(@PathVariable Long id){
        wishListService.deleteProduct(id);
        return new ResponseEntity<>("Delete success!",HttpStatus.OK);
    }
}
