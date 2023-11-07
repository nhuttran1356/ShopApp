package com.nhuttran.shopapp_ecom.service.Impl;

import com.nhuttran.shopapp_ecom.dto.CategoryDTO;
import com.nhuttran.shopapp_ecom.model.CategoryEntity;

import java.util.List;

public interface CategoryServiceImpl {
    CategoryEntity createCategory(CategoryDTO category);
    CategoryEntity getCategoryById(long id);
    List<CategoryEntity> getAllCategory();
    CategoryEntity updateCategory(long categoryId, CategoryDTO category);
    void deleteCategory(long id);
}
