package com.bala.miniecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bala.miniecom.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobile(String mobile);
    Optional<User> findByEmail(String email);
   
}