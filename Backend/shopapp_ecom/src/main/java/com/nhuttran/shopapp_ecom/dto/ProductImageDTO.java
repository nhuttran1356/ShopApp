package com.nhuttran.shopapp_ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhuttran.shopapp_ecom.model.ProductEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's id must be > 0")
    private Long productId;
    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "image's name")
    private String imageUrl;

}
