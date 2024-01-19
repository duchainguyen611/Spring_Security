package com.ra.controller.admin;

import com.ra.model.dto.response.OrdersResponse;
import com.ra.model.entity.ENUM.StatusOrders;
import com.ra.model.entity.Orders;
import com.ra.service.orders.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort")String sort){
        Pageable pageable = PageRequest.of(page,limit, Sort.by(sort));
        Page<OrdersResponse> orders = ordersService.getAll(pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{status}")
    public ResponseEntity<?> getAllByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "id", name = "sort")String sort){
        Pageable pageable = PageRequest.of(page,limit, Sort.by(sort));
        for (StatusOrders stOrders : StatusOrders.values()){
            if (status.equalsIgnoreCase(stOrders.toString())){
                Page<Orders> orders = ordersService.getAllByStatus(stOrders,pageable);
                return new ResponseEntity<>(orders, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
    }
}
