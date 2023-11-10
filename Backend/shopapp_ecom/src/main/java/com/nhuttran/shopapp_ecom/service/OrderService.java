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
import java.util.Optional;

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
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public OrderEntity updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundExeption {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundExeption("Can not find order with id " + id));
        UserEntity existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundExeption("Can not find user with id " + id));

        //mapper
        modelMapper.typeMap(OrderDTO.class, OrderEntity.class)
                .addMappings(mapper -> mapper.skip(OrderEntity::setId));

        //update order
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);

    }

    @Override
    public void deleteOrder(Long id) {
        OrderEntity order = orderRepository.findById(id).orElse(null);
        // Xoa mem
        if(order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }

    }

    @Override
    public List<OrderEntity> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
