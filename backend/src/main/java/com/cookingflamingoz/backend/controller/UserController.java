package com.cookingflamingoz.backend.controller;

import com.cookingflamingoz.backend.model.LoginRequest;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.model.UserCreationResult;
import com.cookingflamingoz.backend.model.UserEnrolleeDTO;
import com.cookingflamingoz.backend.service.UserService;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    //register
    @PostMapping
    public UserCreationResult createUser(@RequestBody UserEnrolleeDTO userEnrolleeDTO) {
        System.out.println("Creating user: ");
        System.out.println(userEnrolleeDTO);

        return userService.saveUser(userEnrolleeDTO);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        UserCreationResult a = userService.getUserByUsernameOrEmail(loginRequest.getEmail(),loginRequest.getPassword());
        if (a.getUser() == null){
            // error logging in -> ask for password reset
            // -> TODO: check error type -> see UserCreationResult
            return ResponseEntity.status(401).body(a.getMessage());
        }

        // TODO: generate token or session here
        //logged in -> now redirect properly
        String token = jwtUtil.generateToken(a.getUser().getId().toString());
        return ResponseEntity.ok(Map.of("token", token)); //NOTE: using map just becouse that will convert automatically to json -> consider making a model for this

    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}