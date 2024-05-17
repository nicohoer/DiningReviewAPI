package com.example.DiningReviewAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private String submittedFrom;
    private Long restaurantId;
    private String review;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;

    private ReviewStatus status;
}
