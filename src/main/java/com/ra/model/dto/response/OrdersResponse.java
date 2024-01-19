package com.ra.model.dto.response;

import com.ra.model.entity.ENUM.StatusOrders;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrdersResponse {
    private Long id;
    private String serialNumber;
    private String totalPrice;
    private StatusOrders statusOrders;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private LocalDate createdAt;
    private LocalDate receivedAt;
    private Long userId;

}
