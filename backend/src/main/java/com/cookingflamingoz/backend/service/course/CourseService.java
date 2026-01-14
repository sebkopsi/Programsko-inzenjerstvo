package com.cookingflamingoz.backend.service.course;

import com.cookingflamingoz.backend.controller.course.CourseRequests;
import com.cookingflamingoz.backend.model.*;
import com.cookingflamingoz.backend.repository.CourseRepository;
import com.cookingflamingoz.backend.repository.CourseTagRepository;
import com.cookingflamingoz.backend.repository.TagRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;
    private final CourseTagRepository courseTagRepository;

    public CourseService(CourseRepository courseRepository, TagRepository tagRepository, CourseTagRepository courseTagRepository, UserRepository userRepository){
        this.courseRepository = courseRepository;
        this.tagRepository = tagRepository;
        this.courseTagRepository = courseTagRepository;
        this.userRepository = userRepository;
    }

    public CourseResults.GetByIdResult getById(int id){
        Optional<Course> course =  courseRepository.findById(id);
        return course.map(value -> new CourseResults.GetByIdResult(true, "", value)).orElseGet(() -> new CourseResults.GetByIdResult(false, "course not found", null));
    }

    public CourseResults.SearchResult search(CourseRequests.SearchRequest request) {
        Set<Course> data = switch (request.scope) {
            case "my" -> new HashSet<>(courseRepository.search(request.term))
                    .stream()
                    .filter(course -> course.getCreator().getId().equals(request.userId))
                    .collect(Collectors.toSet());
            default -> new HashSet<>(courseRepository.search(request.term));
        };

        return new CourseResults.SearchResult(
                true,
                "Found " + data.size() + " courses for search " + request.term + ".",
                data
        );
    }

    public CourseResults.CreateResult create(Integer userId, CourseRequests.CreateRequest request){
        User user = userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;
        if(user == null) {
            return new CourseResults.CreateResult(false, "user does not exist", null);
        }

        Course newCourse = new Course();
        newCourse.setName(request.name);
        newCourse.setDescription(request.desc);
        newCourse.setCreator(user);
        newCourse = courseRepository.save(newCourse);

        Set<Tag> tags = tagRepository.findByNameIn(request.tags);

        Course finalNewCourse = newCourse;
        Set<CourseTag> courseTags = tags.stream().map(tag -> {
            CourseTag courseTag = new CourseTag();
            courseTag.setCourse(finalNewCourse);
            courseTag.setTag(tag);
            return courseTag;
        }).collect(Collectors.toSet());

        courseTagRepository.saveAll(courseTags);

        return new CourseResults.CreateResult(true, "", finalNewCourse);
    }
}
