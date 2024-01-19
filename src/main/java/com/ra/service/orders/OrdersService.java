package com.ra.service.orders;

import com.ra.model.dto.response.OrdersResponse;
import com.ra.model.entity.ENUM.StatusOrders;
import com.ra.model.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrdersService {
    Page<OrdersResponse> getAll(Pageable pageable);
    Page<Orders> getAllByStatus(StatusOrders statusOrders, Pageable pageable);

}
