package com.bala.miniecom.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String mobile;

    @Column(unique = true)
    private String email;
    
    // 🔥 Add this
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference   // ✅ ADD THIS
    private List<UserAddress> addresses;
}