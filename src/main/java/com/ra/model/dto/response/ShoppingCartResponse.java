package com.ra.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShoppingCartResponse {
    String productName;
    Float unitPrice;
    Integer orderQuantity;
}
