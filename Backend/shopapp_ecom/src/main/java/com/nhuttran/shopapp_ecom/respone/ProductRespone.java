package com.nhuttran.shopapp_ecom.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhuttran.shopapp_ecom.model.ProductEntity;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRespone extends BaseRespone {
    private String name;
    private Float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;

    public static ProductRespone fromProduct(ProductEntity productEntity){
        ProductRespone productRespone = ProductRespone.builder()
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .thumbnail(productEntity.getThumbnail())
                .description(productEntity.getDescription())
                .categoryId(productEntity.getCategory().getId())
                .build();
        productRespone.setCreateAt(productEntity.getCreatedAt());
        productRespone.setUpdateAt(productEntity.getUpdatedAt());
        return productRespone;
    }
}
