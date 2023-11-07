package com.nhuttran.shopapp_ecom.repository;

import com.nhuttran.shopapp_ecom.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // tim don hang user
    List<OrderEntity> findByUserId(Long userId);
}
