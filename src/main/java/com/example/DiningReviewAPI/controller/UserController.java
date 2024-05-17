package com.example.DiningReviewAPI.controller;

import com.example.DiningReviewAPI.model.User;
import com.example.DiningReviewAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }




    @PostMapping
    public void addUser(@RequestBody User user){
        validateUserIsValid(user);
        userRepository.save(user);
    }


    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        validateUsername(username);
        Optional<User> userToBeFetched = userRepository.findByUsername(username);
        if (userToBeFetched.isEmpty()) {
            return null;
        }
        User user = userToBeFetched.get();
        return user;
    }


    @PutMapping("/{username}")
    public void updateUser(@PathVariable("username") String username, @RequestBody User userUpdate){
        validateUsername(username);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User userExisting = optionalUser.get();
        if(userUpdate.getUsername() == null) {
            throw new ResponseStatusException((HttpStatus.NOT_ACCEPTABLE));
        }
        copyUserInfoFrom(userExisting, userUpdate);
        userRepository.save(userExisting);
    }
    private void copyUserInfoFrom(User updatedUser, User existingUser) {
        if (ObjectUtils.isEmpty(updatedUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (!ObjectUtils.isEmpty(updatedUser.getCity())) {
            existingUser.setCity(updatedUser.getCity());
        }
        if (!ObjectUtils.isEmpty(updatedUser.getState())) {
            existingUser.setState(updatedUser.getState());
        }
        if (!ObjectUtils.isEmpty(updatedUser.getZipcode())) {
            existingUser.setZipcode(updatedUser.getZipcode());
        }
        if (!ObjectUtils.isEmpty(updatedUser.getPeanutAllergy())) {
            existingUser.setPeanutAllergy(updatedUser.getPeanutAllergy());
        }
        if (!ObjectUtils.isEmpty(updatedUser.getDairyAllergy())) {
            existingUser.setDairyAllergy(updatedUser.getDairyAllergy());
        }
        if (!ObjectUtils.isEmpty(updatedUser.getEggAllergy())) {
            existingUser.setEggAllergy(updatedUser.getEggAllergy());
        }
    }
    private void validateUserIsValid(User user) {
        validateUsername(user.getUsername());

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    private void validateUsername(String userName) {
        if (ObjectUtils.isEmpty(userName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}
