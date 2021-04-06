package com.shop.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "`order`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order{
    @AllArgsConstructor
    @Getter
    public enum Status {
        STATUS_WAIT_CONFIRM(0, "STATUS_WAIT_CONFIRM"),
        STATUS_WAIT_GOOD(1, "STATUS_WAIT_GOOD"),
        STATUS_DELIVERY_PROGRESS(2, "STATUS_DELIVERY_PROGRESS"),
        STATUS_DELIVERY(3, "STATUS_DELIVERY"),
        STATUS_CANCELLED(4, "STATUS_CANCELLED");
        private final int value;
        private final String name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "shipping")
    private Double shipping;

    @Column(name = "grand_total")
    private Double grandTotal;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Date createAt;

    @Column(name = "updated_at")
    private Date updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, //
            foreignKey = @ForeignKey(name = "TRANSACTION_USER_FK"))
    private User account;

}
