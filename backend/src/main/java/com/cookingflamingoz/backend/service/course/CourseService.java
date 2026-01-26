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
import com.cookingflamingoz.backend.repository.EnrolledCourseRepository;
import com.cookingflamingoz.backend.util.GenericResult;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Date;

@Service
public class CourseService {
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;
    private final CourseTagRepository courseTagRepository;
    private final EnrolledCourseRepository enrolledCourseRepository;

    public CourseService(CourseRepository courseRepository, TagRepository tagRepository, CourseTagRepository courseTagRepository, UserRepository userRepository, EnrolledCourseRepository enrolledCourseRepository) {
        this.courseRepository = courseRepository;
        this.tagRepository = tagRepository;
        this.courseTagRepository = courseTagRepository;
        this.userRepository = userRepository;
        this.enrolledCourseRepository = enrolledCourseRepository;
    }

    public CourseResults.GetByIdResult getById(int id, int userId){
        Optional<Course> course =  courseRepository.findById(id);
        User user = userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;
        if(user == null) {
            return new CourseResults.GetByIdResult(false, "user does not exist", null);
        }
        return  course.map(value -> {
            var  response = new CourseResults.GetByIdResult(true, "", value);
            response.data.isUserInstructor = value.getCreator().getUserId().equals(userId);
            return response;
        }).orElseGet(() -> new CourseResults.GetByIdResult(false, "course not found", null)
        );
    }

    public CourseResults.SearchResult search(CourseRequests.SearchRequest request) {
        Set<Course> data = switch (request.scope) {
            case "my" ->  new HashSet<>(courseRepository.search(request.term)).stream().filter(course -> course.getCreator().getUserId().equals(request.userId)).collect(Collectors.toSet());
            default -> new HashSet<>(courseRepository.search(request.term));
        };

        return new CourseResults.SearchResult(
                true,
                "Found " + data.size() + " courses for search " + request.term + ".",
                data
        );
    }

    public CourseResults.CreateResult create(Integer userId, CourseRequests.CreateRequest request){
        if(userId == null || request == null){
            return new CourseResults.CreateResult(false, "missing parameters", null);
        }

        if(request.desc.isEmpty() || request.name.isEmpty() ){
            return new CourseResults.CreateResult(false, "missing name or description", null);
        }


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

public GenericResult enroll(int courseId, int userId) {
    Optional<Course> courseOpt = courseRepository.findById(courseId);
    if (courseOpt.isEmpty()) {
        return new GenericResult(false, "Course not found.");
    }

    Optional<User> userOpt = userRepository.findById(userId);
    if (userOpt.isEmpty()) {
        return new GenericResult(false, "User does not exist.");
    }

    EnrolledCourse.EnrolledCourseId enrollmentId = new EnrolledCourse.EnrolledCourseId(courseId, userId);
    if (enrolledCourseRepository.existsById(enrollmentId)) {
        return new GenericResult(false, "You are already enrolled in this course.");
    }

    EnrolledCourse enrollment = new EnrolledCourse();
    enrollment.setCourse(courseOpt.get());
    enrollment.setUser(userOpt.get());
    enrollment.setCompletionPercentage(0);
    enrollment.setEnrolledAt(new Date()); 
    enrollment.setStatus("enrolled"); 

    enrolledCourseRepository.save(enrollment);

    return new GenericResult(true, "Successfully enrolled in the course.");
}
}
