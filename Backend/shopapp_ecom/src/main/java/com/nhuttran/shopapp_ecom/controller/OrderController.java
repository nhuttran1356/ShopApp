package com.nhuttran.shopapp_ecom.controller;

import com.nhuttran.shopapp_ecom.dto.OrderDTO;
import com.nhuttran.shopapp_ecom.model.OrderEntity;
import com.nhuttran.shopapp_ecom.service.Impl.OrderServiceImpl;
import com.nhuttran.shopapp_ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${v1API}/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                String errorMessage = result.getFieldError().getDefaultMessage();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            OrderEntity order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") long userId) {
        try {
            List<OrderEntity> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") long orderId) {
        try {
            OrderEntity existingOrder = orderService.getOrder(orderId);
            return ResponseEntity.ok(existingOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") long id,
                                         @Valid @RequestBody OrderDTO orderDTO){
        try{
            OrderEntity order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Xoa mem => active = false
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") long id){

        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully with id" + id);
    }
}
