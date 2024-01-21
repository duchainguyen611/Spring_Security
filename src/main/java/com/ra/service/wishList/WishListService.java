package com.ra.service.wishList;

import com.ra.model.dto.request.WishListRequest;
import com.ra.model.dto.response.WishListResponse;
import com.ra.model.entity.Wish_List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishListService {
    Wish_List addProduct(Wish_List wishList);

    Page<WishListResponse> getAll(Pageable pageable);

    void deleteProduct(Long id);

    WishListResponse convertWishListToWishListResponse(Wish_List wishList);

    Wish_List convertWishListRequestToWishListR(WishListRequest wishListRequest);
}
