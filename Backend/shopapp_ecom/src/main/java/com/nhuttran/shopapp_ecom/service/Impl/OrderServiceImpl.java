package com.nhuttran.shopapp_ecom.service.Impl;

import com.nhuttran.shopapp_ecom.dto.OrderDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.model.OrderEntity;


import java.util.List;

public interface OrderServiceImpl {
    OrderEntity createOrder(OrderDTO orderDTO) throws Exception;

    OrderEntity getOrder(Long id);

    OrderEntity updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<OrderEntity> findByUserId(Long userId);
}
