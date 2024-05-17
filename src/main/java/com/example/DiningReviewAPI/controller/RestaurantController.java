package com.example.DiningReviewAPI.controller;


import com.example.DiningReviewAPI.model.Restaurant;
import com.example.DiningReviewAPI.repository.RestaurantRepository;
import com.example.DiningReviewAPI.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RequestMapping("/restaurants")
@RestController
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final Pattern zipCodePattern = Pattern.compile("\\d{5}");
    public RestaurantController(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    public void submitRestaurant(@RequestBody Restaurant restaurant){
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantByZipAndName(restaurant.getZip(), restaurant.getName());
        if(!optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        restaurantRepository.save(restaurant);
    }

    @GetMapping
    public Iterable<Restaurant> getRestaurants(){
        return restaurantRepository.findAll();
    }


    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable("restaurantId") Long restaurantId){
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Restaurant restaurant = optionalRestaurant.get();
        return restaurant;
    }
    @GetMapping("/search")
    public List<Restaurant> searchRestaurants(@RequestParam String zip, @RequestParam String allergy){
        validateZipCode(zip);
        if(allergy.equalsIgnoreCase("peanut")){
            return restaurantRepository.findRestaurantByZipAndPeanutScoreNotNullOrderByPeanutScore(zip);
        }
        else if(allergy.equalsIgnoreCase("dairy")){
            return restaurantRepository.findRestaurantByZipAndDairyScoreNotNullOrderByPeanutScore(zip);
        }
        else if (allergy.equalsIgnoreCase("egg")){
            return restaurantRepository.findRestaurantByZipAndEggScoreNotNullOrderByPeanutScore(zip);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }



    private void validateZipCode(String zipcode) {
        if (!zipCodePattern.matcher(zipcode).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }




}
