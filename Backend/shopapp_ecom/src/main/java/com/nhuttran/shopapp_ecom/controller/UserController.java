package com.nhuttran.shopapp_ecom.controller;

import com.nhuttran.shopapp_ecom.dto.UserDTO;
import com.nhuttran.shopapp_ecom.dto.UserLoginDTO;
import com.nhuttran.shopapp_ecom.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${v1API}/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try {
            if (result.hasErrors()) {
                String errorMessage = result.getFieldError().getDefaultMessage();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword()))
                return ResponseEntity.badRequest().body("Password does not match");

            userService.createUser(userDTO);
            return new ResponseEntity<>("Register successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        return new ResponseEntity<>("Token" , HttpStatus.OK);
    }
}
