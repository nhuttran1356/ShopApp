package com.nhuttran.shopapp_ecom.controller;

import com.nhuttran.shopapp_ecom.dto.CategoryDTO;
import com.nhuttran.shopapp_ecom.model.CategoryEntity;
import com.nhuttran.shopapp_ecom.service.CategoryService;
import com.nhuttran.shopapp_ecom.service.Impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${v1API}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult result){
        if(result.hasErrors()){
            String errorMessage = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>("insert categories successfully", HttpStatus.OK);
    }

    @GetMapping("") // ?page=1&limit=10
    public ResponseEntity<?> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){

        List<CategoryEntity> categories = categoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategories(@PathVariable long id, @Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>("update categories successfully" + id, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategories(@PathVariable long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("delete categories successfully" + id, HttpStatus.OK);
    }
}
