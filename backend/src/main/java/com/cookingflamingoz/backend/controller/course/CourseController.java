package com.cookingflamingoz.backend.controller.course;

import com.cookingflamingoz.backend.service.user.UserService;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cookingflamingoz.backend.model.Course;
import com.cookingflamingoz.backend.service.course.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private JwtUtil jwtUtil;

    private final CourseService courseService;

    public CourseController(CourseService courseService) { this.courseService = courseService; }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Integer id) {
        return courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

}
