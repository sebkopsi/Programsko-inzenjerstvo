package com.cookingflamingoz.backend.controller.user;

import com.cookingflamingoz.backend.model.Tag;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.service.user.UserCreationResult;
import com.cookingflamingoz.backend.service.user.UserProfileResult;
import com.cookingflamingoz.backend.service.user.UserService;
import com.cookingflamingoz.backend.util.GenericResult;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("my")
    public UserProfileResult getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return userService.GetProfile(userID);
    }

    //register
    @PostMapping
    public UserCreationResult createUser(@RequestBody SignUpRequest userEnrolleeDTO) {
        return userService.saveUser(userEnrolleeDTO);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        UserCreationResult a = userService.Login(loginRequest);
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

    @PostMapping("/oauth")
    public ResponseEntity<?>  oauth(@RequestBody OauthRequest oauthRequest) {
        UserCreationResult a = userService.Oauth(oauthRequest);

        if (!a.isSuccess()){
            return ResponseEntity.status(401).body(a.getMessage());
        }
        String token = jwtUtil.generateToken(a.getUserID());
        return  ResponseEntity.ok(Map.of("token", token));
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @GetMapping("/tag")
    public Set<Tag> getuserTags(@RequestParam boolean preferred) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return userService.getTags(userID, preferred);
    }

    @PostMapping("/tag")
    public GenericResult addTag(@RequestBody TagRequest tagRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        if(ObjectUtils.isEmpty(tagRequest.name) || ObjectUtils.isEmpty(tagRequest.category)) {
            return GenericResult.Failure("missing parameters");
        }
        return userService.AddTag(userID, tagRequest.name, tagRequest.category, tagRequest.preferred, true);
    }

     @PatchMapping("/my/name")
    public GenericResult updateMyName(@RequestBody Map<String, String> data) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(auth.getPrincipal().toString());

        return userService.updateName(userID, data.get("firstname"), data.get("surname"));
    }


    @PatchMapping("/my/email")
    public GenericResult updateMyEmail(@RequestBody Map<String, String> data) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(auth.getPrincipal().toString());

        return userService.updateEmail(userID, data.get("email"));
    }


    @PatchMapping("/my/username")
    public GenericResult updateMyUsername(@RequestBody Map<String, String> data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());

        String newUsername = data.get("username");
        if (newUsername == null || newUsername.trim().isEmpty()) {
            return GenericResult.Failure("Username cannot be empty");
        }

        return userService.updateUsername(userID, newUsername);
    }


    @PatchMapping("/my/skill-level")
    public GenericResult updateMySkillLevel(@RequestBody Integer skillLevelId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());

        if (skillLevelId == null) {
            return GenericResult.Failure("skillLevelId is required");
        }

        return userService.updateSkillLevel(userID, skillLevelId);
    }

}


   
