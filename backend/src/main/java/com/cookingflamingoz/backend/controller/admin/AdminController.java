package com.cookingflamingoz.backend.controller.admin;

import com.cookingflamingoz.backend.service.admin.AdminResults;
import com.cookingflamingoz.backend.service.admin.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/inbox")
    public AdminResults.InboxResult findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return adminService.getInbox(userID);
    }

    @GetMapping("/request/{id}")
    public AdminResults.DetailsResult getById(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return adminService.getById(id, userID);
    }

    @PatchMapping("/request/{id}/status")
    public com.cookingflamingoz.backend.util.GenericResult updateStatus(
            @PathVariable Integer id,
            @RequestBody java.util.Map<String, String> body) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());

        String newStatus = body.get("status");

        return adminService.updateStatus(id, newStatus, userID);
    }


}