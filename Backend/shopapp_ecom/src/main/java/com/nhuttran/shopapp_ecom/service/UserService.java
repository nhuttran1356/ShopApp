package com.nhuttran.shopapp_ecom.service;

import com.nhuttran.shopapp_ecom.dto.UserDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.model.RoleEntity;
import com.nhuttran.shopapp_ecom.model.UserEntity;
import com.nhuttran.shopapp_ecom.repository.RoleRepository;
import com.nhuttran.shopapp_ecom.repository.UserRepository;
import com.nhuttran.shopapp_ecom.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public UserEntity createUser(UserDTO userDTO) throws DataNotFoundExeption {
        String phoneNumber = userDTO.getPhoneNumber();
        //Kiem tra sdt neu da co
        if(userRepository.existsByPhoneNumber(phoneNumber))
            throw new DataIntegrityViolationException("Phone number already exists");
        // chuyen DTO thanh Entity
        UserEntity newUser = UserEntity.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        RoleEntity role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundExeption("Role not found"));
        newUser.setRole(role);

        // Kiem tra neu co accountId, khong yeu cau mat khau
        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0){
            String password = userDTO.getPassword();
            // ma hoa pass word

        }

        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {


        return null;
    }
}
