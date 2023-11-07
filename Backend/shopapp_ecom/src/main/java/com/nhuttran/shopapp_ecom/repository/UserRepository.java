package com.nhuttran.shopapp_ecom.repository;

import com.nhuttran.shopapp_ecom.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Kiem tra sdt ton tai
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
