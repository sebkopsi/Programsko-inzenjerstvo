package com.cookingflamingoz.backend.service.instructor;

import com.cookingflamingoz.backend.controller.course.CourseRequests;
import com.cookingflamingoz.backend.model.*;
import com.cookingflamingoz.backend.repository.*;
import com.cookingflamingoz.backend.service.course.CourseResults;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;
    private final CourseTagRepository courseTagRepository;
    private final InstructorProfileRepository instructorProfileRepository;
    private final RequestRepository requestRepository;

    public InstructorService(CourseRepository courseRepository, TagRepository tagRepository, CourseTagRepository courseTagRepository, UserRepository userRepository, InstructorProfileRepository instructorProfileRepository, RequestRepository requestRepository){
        this.courseRepository = courseRepository;
        this.tagRepository = tagRepository;
        this.courseTagRepository = courseTagRepository;
        this.userRepository = userRepository;
        this.instructorProfileRepository = instructorProfileRepository;
        this.requestRepository = requestRepository;
    }

    public  InstructorResult.GetListByIdResult getRequestByUserId(int userId){
        List<Request> allRequests =  requestRepository.findAllBySentByUserId(userId);
        List<Request> pendingRequests = allRequests.stream()
                .filter(r -> {
                    return "promoteInstructor".equals(r.getType());
                })
                .collect(Collectors.toList());
        return new InstructorResult.GetListByIdResult(true, "Requests fetched successfully", pendingRequests);

    }


    // TODO: fix this (what with files?)
    public GenericResult createPromotionRequest(int userId, String title, String content) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new GenericResult(false, "user does not exist");
        }

        // Optional: prevent duplicate pending promote requests
        List<Request> existing = requestRepository.findAllBySentByUserId(userId).stream()
                .filter(r -> "promoteInstructor".equals(r.getType()) && "pending".equals(r.getStatus()))
                .collect(Collectors.toList());
        if (!existing.isEmpty()) {
            return new GenericResult(false, "there is already a pending promotion request");
        }

        Request req = new Request();
        req.setType("promoteInstructor");
        req.setSentByUserId(userId);
        req.setTitle(title == null || title.isEmpty() ? "Promotion request" : title);
        req.setContent(content == null ? "" : content);
        // status defaults to "pending" per model; createdAt handled by DB
        Request saved = requestRepository.save(req);

        return new GenericResult(true, "request created with id " + saved.getReqId());
    }





}
