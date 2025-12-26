package com.cookingflamingoz.backend.service.course;


import com.cookingflamingoz.backend.model.*;
import com.cookingflamingoz.backend.repository.*;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseService {

    @PersistenceContext
    private EntityManager entityManager;
    private final CourseRepository CourseRepository;

    public Course findById(int id) {
        return entityManager.find(Course.class, id);
    }

}