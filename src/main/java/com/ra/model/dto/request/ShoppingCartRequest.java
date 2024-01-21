package com.ra.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShoppingCartRequest {
    Long productId;
    Integer orderQuantity;
}
