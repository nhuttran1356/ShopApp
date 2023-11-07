package com.nhuttran.shopapp_ecom.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "category's name can not be emty")
    private String name;
}
