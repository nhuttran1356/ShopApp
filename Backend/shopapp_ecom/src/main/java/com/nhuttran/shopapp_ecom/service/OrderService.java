package com.nhuttran.shopapp_ecom.service;

import com.nhuttran.shopapp_ecom.dto.OrderDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.model.OrderEntity;
import com.nhuttran.shopapp_ecom.model.OrderStatus;
import com.nhuttran.shopapp_ecom.model.UserEntity;
import com.nhuttran.shopapp_ecom.repository.OrderRepository;
import com.nhuttran.shopapp_ecom.repository.UserRepository;
import com.nhuttran.shopapp_ecom.service.Impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceImpl {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    private final ModelMapper modelMapper;


    @Override
    public OrderEntity createOrder(OrderDTO orderDTO) throws Exception {
        //check user id co ton tai hay khong
        UserEntity user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundExeption("Can not find user with id: " + orderDTO.getUserId()));

        //orderDTO -> order
        modelMapper.typeMap(OrderDTO.class, OrderEntity.class)
                .addMappings(mapper -> mapper.skip(OrderEntity::setId));
        OrderEntity order = new OrderEntity();
        modelMapper.map(orderDTO,order);

        order.setUser(user);
        order.setOrderDate(new Date()); // lay time hien tai
        order.setStatus(OrderStatus.PENDING);
        //shipping date > hom nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();

        if(shippingDate.isBefore(LocalDate.now()))
            throw new DataNotFoundExeption("Date must be at least Today!");

        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public OrderEntity getOrder(Long id) {
        return null;
    }

    @Override
    public OrderEntity updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<OrderEntity> findByUserId(Long userId) {
        return null;
    }
}
