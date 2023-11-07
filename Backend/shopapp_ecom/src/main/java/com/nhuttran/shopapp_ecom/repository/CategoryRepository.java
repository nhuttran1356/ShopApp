package com.nhuttran.shopapp_ecom.repository;

import com.nhuttran.shopapp_ecom.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {


}
