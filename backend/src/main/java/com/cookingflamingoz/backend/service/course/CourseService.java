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

    public CourseResults.UpdateResult update(Integer courseId, Integer userId, CourseRequests.UpdateRequest request){
        if(courseId == null || userId == null || request == null){
            return new CourseResults.UpdateResult(false, "missing parameters", null);
        }

        var courseOpt = courseRepository.findById(courseId);
        if(courseOpt.isEmpty()){
            return new CourseResults.UpdateResult(false, "course not found", null);
        }
        var course = courseOpt.get();

        // verify ownership
        if(course.getCreator() == null || !course.getCreator().getUserId().equals(userId)){
            return new CourseResults.UpdateResult(false, "user is not owner of course", null);
        }

        boolean changed = false;
        if(request.name != null && !request.name.isEmpty()){
            course.setName(request.name);
            changed = true;
        }
        if(request.desc != null && !request.desc.isEmpty()){
            course.setDescription(request.desc);
            changed = true;
        }

        // update tags if provided
        if(request.tags != null){
            // remove old tags and add new ones
            // naive approach: delete existing CourseTag entries for this course and add new ones
            // (CourseTagRepository currently does not expose delete by course, so use JPA cascade via entity manager)
            Set<Tag> tags = tagRepository.findByNameIn(request.tags);

            // recreate course tags
            final Course courseRef = course;
            Set<CourseTag> courseTags = tags.stream().map(tag -> {
                CourseTag ct = new CourseTag();
                ct.setCourse(courseRef);
                ct.setTag(tag);
                return ct;
            }).collect(Collectors.toSet());

            course.setTags(courseTags);
            changed = true;
        }

        if(changed){
            course = courseRepository.save(course);
        }

        return new CourseResults.UpdateResult(true, "", course);
    }
}
