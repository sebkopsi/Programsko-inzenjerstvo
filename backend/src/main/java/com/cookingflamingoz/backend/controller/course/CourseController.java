package com.cookingflamingoz.backend.controller.course;

/*
import org.springframework.boot.test.context.SpringBootTest;
*/

@RestController
@RequestMapping("/course")
public class CourseController {

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Integer id) {
        return courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

}