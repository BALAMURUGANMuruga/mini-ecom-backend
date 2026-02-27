package com.bala.miniecom.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service 
public class OtpService {
	private Map<String, String> otpStorage = new HashMap<>();

    public String generateOtp(String mobile) {
        String otp = String.valueOf(new Random().nextInt(9000) + 1000);
        otpStorage.put(mobile, otp);
        System.out.println("OTP for " + mobile + " is: " + otp);
        return otp;
    }

    public boolean verifyOtp(String mobile, String otp) {
        return otp.equals(otpStorage.get(mobile));
    }
}