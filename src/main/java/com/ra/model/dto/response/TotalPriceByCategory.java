package com.ra.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TotalPriceByCategory {
    String categoryName;
    Double totalPrice;
}
