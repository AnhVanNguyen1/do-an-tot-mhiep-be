package com.shop.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "`order_item`")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "content")
    private String content;

    @Column(name = "updated_at")
    private Date updateAt;

    @Column(name = "created_at")
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false, //
            foreignKey = @ForeignKey(name = "ORDER_ORDERITEM_FK"))
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCT_CARTITEM_FK"))
    private Product product;
}
