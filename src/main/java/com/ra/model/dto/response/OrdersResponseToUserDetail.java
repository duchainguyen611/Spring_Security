package com.ra.model.dto.response;

import com.ra.model.entity.ENUM.StatusOrders;
import com.ra.model.entity.Order_Detail;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrdersResponseToUserDetail {
    private String serialNumber;
    private Double totalPrice;
    private StatusOrders statusOrders;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private LocalDate createdAt;
    private LocalDate receivedAt;
    private List<OrderDetailResponse> orderDetailResponses;
}
