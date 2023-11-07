package com.nhuttran.shopapp_ecom.respone;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListRespone {
    private List<ProductRespone> products;
    private int totalPages;
}
