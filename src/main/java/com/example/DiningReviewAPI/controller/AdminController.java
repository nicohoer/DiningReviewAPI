package com.example.DiningReviewAPI.controller;

import com.example.DiningReviewAPI.model.AdminApproval;
import com.example.DiningReviewAPI.model.Restaurant;
import com.example.DiningReviewAPI.model.Review;
import com.example.DiningReviewAPI.model.ReviewStatus;
import com.example.DiningReviewAPI.repository.RestaurantRepository;
import com.example.DiningReviewAPI.repository.ReviewRepository;
import com.example.DiningReviewAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@RestController
public class AdminController {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public AdminController(UserRepository userRepository, RestaurantRepository restaurantRepository, ReviewRepository reviewRepository){
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
    }
    @GetMapping("/reviewsPending")
    public List<Review> getPendingReviews(){
        return reviewRepository.findReviewsByStatus(ReviewStatus.PENDING);
    }
    @PutMapping("/reviewsPending/{reviewId}")
    public void giveApproval(@PathVariable("reviewId") Long reviewId, @RequestBody AdminApproval adminApproval){
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Review review = optionalReview.get();
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(review.getRestaurantId());
        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(adminApproval.getApproved()){
            review.setStatus(ReviewStatus.ACCEPTED);
        }
        else {
            review.setStatus(ReviewStatus.REJECTED);
        }
        reviewRepository.save(review);
        updateRestaurantScores(optionalRestaurant.get());
    }

    private void updateRestaurantScores(Restaurant restaurant){
        List<Review> reviews = reviewRepository.findReviewsByRestaurantIdAndStatus(restaurant.getId(), ReviewStatus.ACCEPTED);
        if (reviews.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        int peanutSum = 0;
        int peanutCount = 0;
        int dairySum = 0;
        int dairyCount = 0;
        int eggSum = 0;
        int eggCount = 0;
        for (Review r : reviews){
            if(!ObjectUtils.isEmpty(r.getPeanutScore())){
                peanutSum += r.getPeanutScore();
                peanutCount++;
            }
            if(!ObjectUtils.isEmpty(r.getDairyScore())){
                dairySum += r.getDairyScore();
                dairyCount++;
            }
            if(!ObjectUtils.isEmpty(r.getEggScore())) {
                eggSum += r.getEggScore();
                eggCount++;
            }
        }
        int totalCount = peanutCount + eggCount + dairyCount;
        int totalSum = peanutSum + eggSum + dairySum;
        float overallScore = (float) totalSum / totalCount;
        restaurant.setOverallScore(decimalFormat.format(overallScore));
        if(peanutCount > 0){
            float peanutScore = (float) peanutSum / peanutCount;
            restaurant.setPeanutScore(decimalFormat.format(peanutScore));
        }
        if(dairyCount > 0){
            float dairyScore = (float) dairySum / dairyCount;
            restaurant.setDairyScore(decimalFormat.format(dairyScore));
        }
        if(eggCount > 0){
            float eggScore = (float) eggSum / eggCount;
            restaurant.setEggScore(decimalFormat.format(eggScore));
        }
        restaurantRepository.save(restaurant);
    }
}
