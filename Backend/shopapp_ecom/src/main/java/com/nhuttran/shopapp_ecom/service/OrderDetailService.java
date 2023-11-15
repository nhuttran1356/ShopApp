package com.nhuttran.shopapp_ecom.service;

import com.nhuttran.shopapp_ecom.dto.OrderDetailDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.model.OrderDetailEntity;
import com.nhuttran.shopapp_ecom.model.OrderEntity;
import com.nhuttran.shopapp_ecom.model.ProductEntity;
import com.nhuttran.shopapp_ecom.repository.OrderDetailRepository;
import com.nhuttran.shopapp_ecom.repository.OrderRepository;
import com.nhuttran.shopapp_ecom.repository.ProductRepository;
import com.nhuttran.shopapp_ecom.service.Impl.OrderDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderDetailService implements OrderDetailServiceImpl {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public OrderDetailEntity createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {

        //tìm xem orderId có tồn tại ko
        OrderEntity order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundExeption(
                        "Cannot find Order with id : "+orderDetailDTO.getOrderId()));
        // Tìm Product theo id
        ProductEntity product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundExeption(
                        "Cannot find product with id: " + orderDetailDTO.getProductId()));
        OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        //lưu vào db
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetailEntity getOrderDetail(Long id) throws DataNotFoundExeption {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundExeption("can not find orderdetail with id" + id));
    }

    @Override
    public OrderDetailEntity updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundExeption {
        //check order detail có tồn tại
        OrderDetailEntity existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundExeption("Cannot find order detail with id: "+id));
        OrderEntity existingOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundExeption("Cannot find order with id: "+id));
        ProductEntity existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundExeption(
                        "Cannot find product with id: " + orderDetailDTO.getProductId()));
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetailEntity> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
