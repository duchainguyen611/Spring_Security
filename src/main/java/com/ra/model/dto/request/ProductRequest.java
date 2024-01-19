package com.ra.model.dto.request;

import com.ra.model.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    @NotEmpty(message = "not Empty")
    @NotBlank(message = "not Blank")
    private String productName;
    @NotNull(message = "not Null")
    private Long categoryId;
    private String description;
    @Min(1)
    private Float unitPrice;
    @Min(0)
    private Integer stockQuantity;
    private String image;
    private LocalDate createdAt;
}
