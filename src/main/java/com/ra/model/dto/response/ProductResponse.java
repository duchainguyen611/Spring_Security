package com.ra.model.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private String productName;
    private String categoryName;
    private String description;
    private Double unitPrice;
    private String image;
}
