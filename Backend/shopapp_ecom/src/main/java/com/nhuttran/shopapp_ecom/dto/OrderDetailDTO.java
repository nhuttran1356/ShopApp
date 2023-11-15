package com.nhuttran.shopapp_ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @Min(value = 1, message = "order_id must be > 0")
    @JsonProperty("order_id")
    private Long orderId;
    @Min(value = 1, message = "product_id must be > 0")
    @JsonProperty("product_id")
    private Long productId;
    @Min(value = 0, message = "price must be >= 0")
    private Float price;
    @JsonProperty("number_of_products")
    private int numberOfProducts;
    @Min(value = 0, message = "total_money must be >= 0")
    @JsonProperty("total_money")
    private Float totalMoney;
    private String color;


}
