package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Shopping_Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderQuantity;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private User user;
}
