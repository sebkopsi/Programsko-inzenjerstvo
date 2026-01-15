package com.cookingflamingoz.backend.controller.course;

import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.service.course.CourseResults;
import com.cookingflamingoz.backend.service.course.CourseService;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private JwtUtil jwtUtil;

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public CourseResults.GetByIdResult getById(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return courseService.getById(id, userID);
    }


    @PostMapping("")
    public CourseResults.CreateResult create(@RequestBody CourseRequests.CreateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return courseService.create(userID, request);
    }

    @PostMapping("/search")
    public CourseResults.SearchResult findAll(@RequestBody CourseRequests.SearchRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        request.userId=userID;
        return courseService.search(request);
    }
}