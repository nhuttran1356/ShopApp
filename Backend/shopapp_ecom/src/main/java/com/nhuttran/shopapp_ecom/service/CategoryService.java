package com.nhuttran.shopapp_ecom.service;

import com.nhuttran.shopapp_ecom.dto.CategoryDTO;
import com.nhuttran.shopapp_ecom.model.CategoryEntity;
import com.nhuttran.shopapp_ecom.repository.CategoryRepository;
import com.nhuttran.shopapp_ecom.service.Impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryEntity createCategory(CategoryDTO categoryDTO) {
        // chuyen DTO sang CategoryEntity
        CategoryEntity newCategory = CategoryEntity
                .builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public CategoryEntity getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found")) ;
    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity updateCategory(long categoryId, CategoryDTO categoryDTO) {
        CategoryEntity existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
