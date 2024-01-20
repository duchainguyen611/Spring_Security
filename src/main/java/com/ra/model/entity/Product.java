package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String sku;
    @Column(unique = true)
    private String productName;
    private String description;
    private Float unitPrice;
    private Integer stockQuantity;
    private String image;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "categoryId",referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Order_Detail> orderDetails;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Shopping_Cart> shoppingCarts;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<Wish_List> wishLists;
}
