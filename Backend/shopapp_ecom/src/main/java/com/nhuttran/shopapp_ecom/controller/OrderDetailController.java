package com.nhuttran.shopapp_ecom.controller;

import com.nhuttran.shopapp_ecom.dto.OrderDetailDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.model.OrderDetailEntity;
import com.nhuttran.shopapp_ecom.respone.OrderDetailRespone;
import com.nhuttran.shopapp_ecom.service.Impl.OrderDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${v1API}/order_details")
public class OrderDetailController {

    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO){

        try {
            OrderDetailEntity newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailRespone.fromOrderDetail(newOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws DataNotFoundExeption {

        OrderDetailEntity orderDetailEntity = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok(OrderDetailRespone.fromOrderDetail(orderDetailEntity));
    }

    // Lay order_detail cua order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailWithOrderId(@Valid @PathVariable("orderId") Long orderId){
        List<OrderDetailEntity> orderDetails =  orderDetailService.findByOrderId(orderId);
        List<OrderDetailRespone> orderDetailRespones = orderDetails.stream()
                .map(OrderDetailRespone::fromOrderDetail).toList();
        return ResponseEntity.ok(orderDetailRespones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
                                               @RequestBody OrderDetailDTO newOrderDetailDTO){
        try {
            OrderDetailEntity orderDetail = orderDetailService.updateOrderDetail(id, newOrderDetailDTO);
            return ResponseEntity.ok().body(orderDetail);
        } catch (DataNotFoundExeption e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        orderDetailService.deleteById(id);
        return ResponseEntity.ok("delete order detail success with id " + id);
    }

}
