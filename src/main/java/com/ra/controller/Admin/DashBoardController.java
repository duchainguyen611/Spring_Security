package com.ra.controller.Admin;

import com.ra.model.dto.response.BestSellerProduct;
import com.ra.model.dto.response.TotalPriceByCategory;
import com.ra.service.dashBoard.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/admin/dash-board/sales")
public class DashBoardController {
    @Autowired
    DashBoardService dashBoardService;

    @GetMapping("/{start}/{end}")
    public ResponseEntity<?> totalPriceByTime(@PathVariable LocalDate start, @PathVariable LocalDate end) {
        Double totalPrice = dashBoardService.totalPriceByTime(start, end);
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }

    @GetMapping("/best-seller-products")
    public ResponseEntity<?> bestSellerProduct(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "productName", name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort));
        Page<BestSellerProduct> bestSellerProducts = dashBoardService.productBestSeller(pageable);
        return new ResponseEntity<>(bestSellerProducts, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> totalPriceByCategory(){
        List<TotalPriceByCategory> totalPriceByCategoryList = dashBoardService.totalPriceByCategory();
        return new ResponseEntity<>(totalPriceByCategoryList,HttpStatus.OK);
    }
}
