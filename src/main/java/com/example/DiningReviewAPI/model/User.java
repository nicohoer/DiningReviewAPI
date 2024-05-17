package com.example.DiningReviewAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name = "USERS")
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String city;
    private String state;
    private String zipcode;
    private String peanutAllergy;
    private String eggAllergy;
    private String dairyAllergy;
}
