package com.shop.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "published")
    private Integer published;

    @Column(name = "publishedAt")
    private Date publishedAt;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCT_PRODUCT_REVIEW_FK"))
    private Product product;
}
