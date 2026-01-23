package com.cookingflamingoz.backend.controller.request;

import com.cookingflamingoz.backend.service.request.RequestService;
import com.cookingflamingoz.backend.util.GenericResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/request")
public class UserRequestController {
    private final RequestService requestService;

    public UserRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/report")
    public GenericResult submitReport(@RequestBody Map<String, Object> body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(auth.getPrincipal().toString());

        // Extracting data from json payload
        String title = body.getOrDefault("title", "Report").toString();
        String content = body.get("content").toString();
        String type = body.get("type").toString();

        // Extracting IDs (can be null)
        Integer targetCourseId = body.get("targetCourseId") != null ? (Integer) body.get("targetCourseId") : null;
        Integer reportedUserId = body.get("reportedUserId") != null ? (Integer) body.get("reportedUserId") : null;

        return requestService.submitReport(userID, title, content, type, targetCourseId, reportedUserId);
    }
}
