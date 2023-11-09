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

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("user_id") long userId) {
        try {
            return ResponseEntity.ok("get order successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") long id,
                                         @Valid @RequestBody OrderDTO orderDTO){

        return ResponseEntity.ok("Cap nhap order");

    }

    //Xoa mem => active = false
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") long id){

        return ResponseEntity.ok("Order deleted successfully with id" + id);
    }
}
