package com.ra.service.wishList;

import com.ra.model.dto.request.WishListRequest;
import com.ra.model.dto.response.WishListResponse;
import com.ra.model.entity.Wish_List;
import com.ra.repository.WishListRepository;
import com.ra.service.product.ProductService;
import com.ra.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListServiceIMPL implements WishListService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    Logger logger = LoggerFactory.getLogger(WishListServiceIMPL.class);

    @Override
    public Wish_List addProduct(Wish_List wishList) {
        return wishListRepository.save(wishList);
    }

    @Override
    public Page<WishListResponse> getAll(Pageable pageable) {
        Page<Wish_List> wishLists = wishListRepository.findAllByUser(userService.userLogin(), pageable);
        return wishLists.map(this::convertWishListToWishListResponse);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Wish_List wish_list = wishListRepository.findByIdAndUser(id,userService.userLogin());
        if (wish_list == null) {
            logger.error("ID not found in this User");
            throw new RuntimeException();
        }
        wishListRepository.deleteByUserAndId(userService.userLogin(), id);
    }

    @Override
    public WishListResponse convertWishListToWishListResponse(Wish_List wishList) {
        return WishListResponse.builder()
                .productName(wishList.getProduct().getProductName())
                .build();
    }

    @Override
    public Wish_List convertWishListRequestToWishListR(WishListRequest wishListRequest) {
        return Wish_List.builder()
                .product(productService.findById(wishListRequest.getProductId()))
                .user(userService.userLogin())
                .build();
    }
}
