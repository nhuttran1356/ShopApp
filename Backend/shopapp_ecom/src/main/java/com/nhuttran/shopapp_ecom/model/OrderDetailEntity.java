package com.nhuttran.shopapp_ecom.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float price;
    @JoinColumn(name = "number_of_products", nullable = false)
    private int numberOfProduct;
    @JoinColumn(name = "total_money", nullable = false)
    private Float totalMoney;
    @JoinColumn(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
