package com.shop.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;
import java.util.Date;

@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "photo_product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer Id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false, //
            foreignKey = @ForeignKey(name = "PRODUCT_CATEGORY_FK"))
    private Product product;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_name_blank")
    private String fileNameBlank;

    @Column(name = "upload_dir")
    private String uploadDir;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;
}