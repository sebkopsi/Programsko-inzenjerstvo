package com.cookingflamingoz.backend.service.course;


import com.cookingflamingoz.backend.model.*;
import com.cookingflamingoz.backend.repository.*;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    @PersistenceContext
    private EntityManager entityManager;
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) { this.courseRepository = courseRepository; }

    // Get course by ID
    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

}