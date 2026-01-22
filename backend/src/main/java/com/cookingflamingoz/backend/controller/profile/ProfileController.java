package com.cookingflamingoz.backend.controller.profile;

import com.cookingflamingoz.backend.service.admin.AdminResults;
import com.cookingflamingoz.backend.service.profile.ProfileService;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) { this.profileService = profileService; }

    /*
    @GetMapping("/my")
    public ProfileResult.InboxResult findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return adminService.getInbox(userID);
    }
    */
}
