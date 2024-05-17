package com.example.DiningReviewAPI.model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String zip;

    private String overallScore;

    private String peanutScore;
    private String dairyScore;
    private String eggScore;
}
