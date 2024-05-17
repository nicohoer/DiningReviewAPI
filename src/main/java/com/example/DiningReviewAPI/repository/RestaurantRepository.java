package com.example.DiningReviewAPI.repository;

import com.example.DiningReviewAPI.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    Optional<Restaurant> findRestaurantByZipAndName(String zip, String name);
    List<Restaurant> findRestaurantByZipAndPeanutScoreNotNullOrderByPeanutScore(String zip);
    List<Restaurant> findRestaurantByZipAndDairyScoreNotNullOrderByPeanutScore(String zip);
    List<Restaurant> findRestaurantByZipAndEggScoreNotNullOrderByPeanutScore(String zip);
}
