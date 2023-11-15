package com.nhuttran.shopapp_ecom.service.Impl;

import com.nhuttran.shopapp_ecom.dto.OrderDetailDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.model.OrderDetailEntity;

import java.util.List;

public interface OrderDetailServiceImpl {
    OrderDetailEntity createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetailEntity getOrderDetail(Long id) throws DataNotFoundExeption;
    OrderDetailEntity updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundExeption;
    void deleteById(Long id);
    List<OrderDetailEntity> findByOrderId(Long orderId);
}
