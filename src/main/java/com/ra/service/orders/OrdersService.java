package com.ra.service.orders;

import com.ra.model.dto.response.OrdersResponseToUser;
import com.ra.model.dto.response.OrdersResponseToAdmin;
import com.ra.model.dto.response.OrdersResponseToUserDetail;
import com.ra.model.entity.ENUM.StatusOrders;
import com.ra.model.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrdersService {
    Page<OrdersResponseToAdmin> getAll(Pageable pageable);
    Page<Orders> getAllByStatus(StatusOrders statusOrders, Pageable pageable);
    void addOrders(Orders orders);

    Page<OrdersResponseToUser> getAllByUser(Pageable pageable);

    List<OrdersResponseToUserDetail> getBySerialNumber(String serial);
}
