package com.nhuttran.shopapp_ecom.repository;

import com.nhuttran.shopapp_ecom.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // tim name san pham ton tai
    boolean existsByName(String name);
    //phan trang
    Page<ProductEntity> findAll(Pageable pageable);
}
