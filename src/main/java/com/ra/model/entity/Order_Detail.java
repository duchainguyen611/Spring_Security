package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double unitPrice;
    private Integer orderQuantity;

    @ManyToOne
    @JoinColumn(name = "orderId",referencedColumnName = "id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private Product product;
}
