package com.cookingflamingoz.backend.controller;

import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.model.UserEnrolleeDTO;
import com.cookingflamingoz.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody UserEnrolleeDTO userEnrolleeDTO) {
        System.out.println("Creating user: ");
        System.out.println(userEnrolleeDTO);
        // TODO: update so that when adding user that exist it throws error os something
        return userService.saveUser(userEnrolleeDTO);
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}