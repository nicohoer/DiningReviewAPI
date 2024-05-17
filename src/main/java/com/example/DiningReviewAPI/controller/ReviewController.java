package com.example.DiningReviewAPI.controller;

import com.example.DiningReviewAPI.model.Restaurant;
import com.example.DiningReviewAPI.model.Review;
import com.example.DiningReviewAPI.model.ReviewStatus;
import com.example.DiningReviewAPI.repository.RestaurantRepository;
import com.example.DiningReviewAPI.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewController(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository){
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }
    @PostMapping
    public void addReview(@RequestBody Review review){
        Optional<Restaurant> reviewedRestaurant = restaurantRepository.findById(review.getRestaurantId());
        if(reviewedRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        review.setStatus(ReviewStatus.PENDING);
        reviewRepository.save(review);
    }
}
