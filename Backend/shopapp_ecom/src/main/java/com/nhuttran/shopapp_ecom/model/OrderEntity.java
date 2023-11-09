package com.nhuttran.shopapp_ecom.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fullname", length = 100)
    private String fullname;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;
    @Column(name = "address", length = 200, nullable = false)
    private String address;
    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "total_money")
    private Float totalMoney;
    @Column(name = "shipping_method", length = 100)
    private String shippingMethod;
    @Column(name = "shipping_address", length = 200)
    private String shippingAddress;
    @Column(name = "shipping_date")
    private LocalDate shippingDate;
    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "payment_method", length = 100)
    private String paymentMethod;
    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
