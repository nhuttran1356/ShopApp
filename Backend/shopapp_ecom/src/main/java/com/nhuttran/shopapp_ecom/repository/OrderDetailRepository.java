package com.nhuttran.shopapp_ecom.repository;

import com.nhuttran.shopapp_ecom.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    List<OrderDetailEntity> findByOrderId(Long orderId);

}
