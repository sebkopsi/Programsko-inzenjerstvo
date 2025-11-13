package com.cookingflamingoz.backend.controller.user;

import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.service.user.UserCreationResult;
import com.cookingflamingoz.backend.service.user.UserService;
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

    @GetMapping("{id}")
    public  User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    //register
    @PostMapping
    public UserCreationResult createUser(@RequestBody SignUpRequest userEnrolleeDTO) {
        return userService.saveUser(userEnrolleeDTO);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        UserCreationResult a = userService.Login(loginRequest.getEmail(),loginRequest.getPassword());
        if (!a.isSuccess()){
            // error logging in -> ask for password reset
            // -> TODO: check error type -> see UserCreationResult
            return ResponseEntity.status(401).body(a.getMessage());
        }

        // TODO: generate token or session here
        //logged in -> now redirect properly
        String token = jwtUtil.generateToken(a.getUserID());
        return ResponseEntity.ok(Map.of("token", token)); //NOTE: using map just becouse that will convert automatically to json -> consider making a model for this

    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}