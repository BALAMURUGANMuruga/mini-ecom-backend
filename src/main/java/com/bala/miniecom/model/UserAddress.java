package com.bala.miniecom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {

    @Id
   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String mobileNumber;
    private String houseNo;
    private String area;
    private String city;
    private String state;
    private String pincode;

    // 🔥 Many addresses belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference 
    private User user;
}