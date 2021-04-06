package com.shop.shop.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private Integer id;

    private String name;

    private String metaTitle;

    private String summary;

    private Integer type;

    private Double price;

    private Double discount;

    private Integer quantity;

    private Date publishedAt;

    private Date startsAt;

    private Date endsAt;

    private String content;

    private MultipartFile photos;

    private String photo;

    private Integer categoryId;
}
