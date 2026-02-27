package com.bala.miniecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bala.miniecom.model.UserAddress;

public interface AddressRepository extends JpaRepository<UserAddress, Long> {
	 List<UserAddress> findByUserId(Long userId); 
   
}