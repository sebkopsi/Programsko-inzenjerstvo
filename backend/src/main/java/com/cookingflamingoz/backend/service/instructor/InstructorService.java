package com.cookingflamingoz.backend.service.instructor;

import com.cookingflamingoz.backend.controller.course.CourseRequests;
import com.cookingflamingoz.backend.controller.instructor.InstructorRequests;
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
    public GenericResult createPromotionRequest(int userId, InstructorRequests.PromotionRequestBody data) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new GenericResult(false, "user does not exist");
        }
        if(data == null){
            return new GenericResult(false, "invalid request data");
        }

        // Optional: prevent duplicate pending promote requests
        List<Request> existing = requestRepository.findAllBySentByUserId(userId).stream()
                .filter(r -> "promoteInstructor".equals(r.getType()) && "pending".equals(r.getStatus()))
                .collect(Collectors.toList());
        if (!existing.isEmpty()) {
            return new GenericResult(false, "there is already a pending promotion request");
        }

        String username = data.username;
        String biography = data.biography;
        String specialization = data.specialization;

        //null for now
        Byte[] profilePicture = data.profilePicture;
        Byte[] identificationDocument = data.identificationDocument;
        Byte[] diploma = data.diploma;

        //check if username doesn't contain invalid characters
        if(!username.matches("^[a-zA-Z0-9_]+$")){
            return new GenericResult(false, "username contains invalid characters");
        }
        //check if username is already taken
        Optional<InstructorProfile> existingProfile = instructorProfileRepository.findByUsername(username);
        if(existingProfile.isPresent()){
            return new GenericResult(false, "username is already taken");
        }

        if (username == null || username.isEmpty()) {
            return new GenericResult(false, "username cannot be empty");
        }
        if (biography == null || biography.isEmpty()) {
            return new GenericResult(false, "biography cannot be empty");
        }
        if (specialization == null || specialization.isEmpty()) {
            return new GenericResult(false, "specialization cannot be empty");
        }

        // compose safely
        StringBuilder sb = new StringBuilder(256);
        sb.append("Username: ").append(username).append("\n")
                .append("Biography: ").append(biography).append("\n")
                .append("Specialization: ").append(specialization).append("\n");
        String content = sb.toString();

        Request req = new Request();
        req.setType("promoteInstructor");
        req.setSentByUserId(userId);
        req.setTitle("Promotion request"); // just this ??
        req.setContent(content);
        req.setReportedUserId(null);
        req.setTargetCourseId(null);
        // status defaults to "pending" per model; createdAt handled by DB
        Request saved = requestRepository.save(req);

        return new GenericResult(true, "request created with id " + saved.getReqId());
    }





}
