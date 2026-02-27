package com.bala.miniecom.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bala.miniecom.model.User;
import com.bala.miniecom.model.UserAddress;
import com.bala.miniecom.repository.AddressRepository;
import com.bala.miniecom.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    

    private final AddressRepository addressRepository;


    public User findOrCreateUser(String mobile, String email) {


        return userRepository.findByMobile(mobile)
                .map(existingUser -> {
                    if (email != null && existingUser.getEmail() == null) {
                        existingUser.setEmail(email);
                        return userRepository.save(existingUser);
                    }
                    return existingUser;
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setMobile(mobile);
                    newUser.setEmail(email);
                    return userRepository.save(newUser);
                });
    }
    
    public UserAddress saveAddress(Long userId, UserAddress address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }
    
    public List<UserAddress> getAddressByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    
    public List<UserAddress> getAddressByMobile(String mobile) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressRepository.findByUserId(user.getId());
    }
    
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
}