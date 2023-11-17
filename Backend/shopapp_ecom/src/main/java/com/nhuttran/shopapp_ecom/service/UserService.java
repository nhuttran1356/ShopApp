package com.nhuttran.shopapp_ecom.service;

import com.nhuttran.shopapp_ecom.component.JwtTokenUtil;
import com.nhuttran.shopapp_ecom.configuration.SecurityConfiguration;
import com.nhuttran.shopapp_ecom.dto.UserDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.model.RoleEntity;
import com.nhuttran.shopapp_ecom.model.UserEntity;
import com.nhuttran.shopapp_ecom.repository.RoleRepository;
import com.nhuttran.shopapp_ecom.repository.UserRepository;
import com.nhuttran.shopapp_ecom.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

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
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);

        }

        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<UserEntity> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty())
            throw new DataNotFoundExeption("Invalid phone number or password");

        UserEntity existingUser = optionalUser.get();

        //check password
        if(existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password, existingUser.getAuthorities());

        //Autheticate
        authenticationManager.authenticate(authenticationToken);
//        String token =
//        System.out.println("token la: " + token);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
