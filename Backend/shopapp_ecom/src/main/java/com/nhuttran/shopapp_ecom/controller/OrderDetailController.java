package com.nhuttran.shopapp_ecom.controller;

import com.nhuttran.shopapp_ecom.dto.OrderDatailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${v1API}/order_details")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDatailDTO orderDatailDTO){

        return ResponseEntity.ok("create order detail success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id){

        return ResponseEntity.ok("get oder detail with id " + id );
    }

    // Lay order_detail cua order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailWithOrderId(@Valid @PathVariable("orderId") Long orderId){
        return ResponseEntity.ok("get order dtail with order id " + orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
                                               @RequestBody OrderDatailDTO newOrderDatailDTO){
        return ResponseEntity.ok("update ordertail with id " + id + "new orderdetail " + newOrderDatailDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){

        return ResponseEntity.ok("delete order detail success with id " + id);
    }

}
