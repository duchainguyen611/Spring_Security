package com.ra.service.orders;

import com.ra.model.dto.response.OrdersResponse;
import com.ra.model.entity.ENUM.StatusOrders;
import com.ra.model.entity.Orders;
import com.ra.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceIMPL implements OrdersService{

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Page<OrdersResponse> getAll(Pageable pageable) {
        Page<Orders> orders = ordersRepository.findAll(pageable);
        return orders.map(this::convertOrdersToOrdersResponse);
    }

    @Override
    public Page<Orders> getAllByStatus(StatusOrders statusOrders, Pageable pageable) {
        return null;
    }

    public OrdersResponse convertOrdersToOrdersResponse(Orders orders){
        return OrdersResponse.builder()
                .id(orders.getId())
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .statusOrders(orders.getStatusOrders())
                .note(orders.getNote())
                .receiveName(orders.getReceiveName())
                .receiveAddress(orders.getReceiveAddress())
                .receivePhone(orders.getReceivePhone())
                .receivedAt(orders.getReceivedAt())
                .userId(orders.getUser().getId())
                .build();
    }
}
