package com.bala.miniecom.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;
import java.util.Map;

import com.bala.miniecom.model.User;
import com.bala.miniecom.model.UserAddress;
import com.bala.miniecom.repository.AddressRepository;
import com.bala.miniecom.repository.UserRepository;

import com.bala.miniecom.service.OtpService;
import com.bala.miniecom.service.UserService;


@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
	
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OtpService otpService;
    private final UserService userService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
    	  String mobile = request.get("mobile");
    	    String email = request.get("email");

    	    if (mobile != null) {
    	        otpService.generateOtp(mobile);
    	    } else if (email != null) {
    	        otpService.generateOtp(email);
    	    } else {
    	        return ResponseEntity.badRequest().body("Mobile or Email required");
    	    }
        return ResponseEntity.ok("OTP Sent");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {

    	  String mobile = request.get("mobile");
    	    String email = request.get("email");
    	    String otp = request.get("otp");

    	    String key = (mobile != null) ? mobile : email;

    	    if (!otpService.verifyOtp(key, otp)) {
    	        return ResponseEntity.badRequest().body("Invalid OTP");
    	    }

    	    User user = userService.findOrCreateUser(mobile, email);

    	    return ResponseEntity.ok(user.getId());
}
    
    @PostMapping("/users/{userId}/address")
    public UserAddress saveAddress(@PathVariable Long userId,
                               @RequestBody UserAddress address) {
        return userService.saveAddress(userId, address);
    }
    
    @GetMapping("/{userId}/address")
    public List<UserAddress> getAddressByUserId(@PathVariable Long userId) {
        return userService.getAddressByUserId(userId);
    }
    
    @GetMapping("/mobile/{mobile}/address")
    public List<UserAddress> getAddressByMobile(@PathVariable String mobile) {
        return userService.getAddressByMobile(mobile);
    }
    
    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {

        if (!addressRepository.existsById(addressId)) {
            return ResponseEntity.notFound().build();
        }

        addressRepository.deleteById(addressId);
        return ResponseEntity.ok("Address deleted successfully");
    }
    
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {

        User user = userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }
}