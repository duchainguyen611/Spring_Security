package com.ra.service.orders;

import com.ra.model.dto.response.OrderDetailResponse;
import com.ra.model.dto.response.OrdersResponseToUser;
import com.ra.model.dto.response.OrdersResponseToAdmin;
import com.ra.model.dto.response.OrdersResponseToUserDetail;
import com.ra.model.entity.ENUM.StatusOrders;
import com.ra.model.entity.Order_Detail;
import com.ra.model.entity.Orders;
import com.ra.repository.OrdersRepository;
import com.ra.service.UserAndRole.UserService;
import com.ra.service.orderDetail.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceIMPL implements OrdersService{

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;


    @Override
    public Page<OrdersResponseToAdmin> getAll(Pageable pageable) {
        Page<Orders> orders = ordersRepository.findAll(pageable);
        return orders.map(this::convertOrdersToOrdersResponseAdmin);
    }

    @Override
    public Page<Orders> getAllByStatus(StatusOrders statusOrders, Pageable pageable) {
        return null;
    }

    @Override
    public void addOrders(Orders orders) {
         ordersRepository.save(orders);
    }

    @Override
    public Page<OrdersResponseToUser> getAllByUser(Pageable pageable) {
        Page<Orders> orders = ordersRepository.findAllByUser(userService.userLogin(),pageable);
        return orders.map(this::convertOrdersToOrdersResponseUser);
    }

    @Override
    public List<OrdersResponseToUserDetail> getBySerialNumber(String serial) {
        List<Orders> orders = ordersRepository.findAllBySerialNumberContainingIgnoreCase(serial);
        return orders.stream()
                .map(this::convertOrdersToOrdersResponseUserDetail)
                .collect(Collectors.toList());
    }

    public OrdersResponseToAdmin convertOrdersToOrdersResponseAdmin(Orders orders){
        return OrdersResponseToAdmin.builder()
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

    public OrdersResponseToUser convertOrdersToOrdersResponseUser(Orders orders){
        return OrdersResponseToUser.builder()
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .statusOrders(orders.getStatusOrders())
                .note(orders.getNote())
                .build();
    }

    public OrdersResponseToUserDetail convertOrdersToOrdersResponseUserDetail(Orders orders){
        List<OrderDetailResponse> orderDetailResponses = orderDetailService.findByOrder(orders);
        return OrdersResponseToUserDetail.builder()
                .serialNumber(orders.getSerialNumber())
                .totalPrice(orders.getTotalPrice())
                .statusOrders(orders.getStatusOrders())
                .note(orders.getNote())
                .receiveName(orders.getReceiveName())
                .receiveAddress(orders.getReceiveAddress())
                .receivePhone(orders.getReceivePhone())
                .createdAt(orders.getCreatedAt())
                .receivedAt(orders.getReceivedAt())
                .orderDetailResponses(orderDetailResponses)
                .build();
    }
}
