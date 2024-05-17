package com.example.DiningReviewAPI.repository;

import com.example.DiningReviewAPI.model.Review;
import com.example.DiningReviewAPI.model.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findReviewsByStatus(ReviewStatus reviewStatus);
    List<Review> findReviewsByRestaurantIdAndStatus(Long id, ReviewStatus reviewStatus);
}
