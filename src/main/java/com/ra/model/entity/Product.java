package com.ra.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(unique = true)
    private String sku;
    @Column(unique = true,nullable = false)
    private String productName;
    private String description;
    private Float unitPrice;
    @Min(0)
    private Integer stockQuantity;
    private String image;
    @Column(name = "created_at", columnDefinition = "DATE")
    private Date createdAt;
    private Date updatedAt;
}
