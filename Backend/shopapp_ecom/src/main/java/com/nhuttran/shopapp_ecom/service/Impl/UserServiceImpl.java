package com.nhuttran.shopapp_ecom.service.Impl;

import com.nhuttran.shopapp_ecom.dto.UserDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.model.UserEntity;

public interface UserServiceImpl {
    UserEntity createUser(UserDTO userDTO) throws DataNotFoundExeption;
    String login(String phoneNumber, String password) throws Exception;
}
