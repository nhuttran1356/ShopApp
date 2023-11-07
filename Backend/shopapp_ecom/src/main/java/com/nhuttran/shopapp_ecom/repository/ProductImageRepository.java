package com.nhuttran.shopapp_ecom.repository;

import com.nhuttran.shopapp_ecom.model.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findByProductId(Long productId);

}
