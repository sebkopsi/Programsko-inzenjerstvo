package com.cookingflamingoz.backend.service.instructor;

import com.cookingflamingoz.backend.controller.instructor.InstructorRequests;
import com.cookingflamingoz.backend.controller.material.MaterialRequests;
import com.cookingflamingoz.backend.model.*;
import com.cookingflamingoz.backend.repository.*;
import com.cookingflamingoz.backend.service.material.MaterialResults;
import com.cookingflamingoz.backend.service.material.MaterialService;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final InstructorProfileRepository instructorProfileRepository;
    private final RequestRepository requestRepository;
    private final MaterialService materialService;

    public InstructorService( UserRepository userRepository, InstructorProfileRepository instructorProfileRepository, RequestRepository requestRepository, MaterialService materialService){
        this.userRepository = userRepository;
        this.instructorProfileRepository = instructorProfileRepository;
        this.requestRepository = requestRepository;
        this.materialService = materialService;
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

        // prefer frontend names if present
        String username = data.username != null && !data.username.isEmpty() ? data.username : data.title;
        String biography = data.biography != null && !data.biography.isEmpty() ? data.biography : data.desc;
        String specialization = data.specialization;

        // validation
        if (username == null || username.isEmpty()) {
            return new GenericResult(false, "username cannot be empty");
        }
        if(!username.matches("^[a-zA-Z0-9_]+$")){
            return new GenericResult(false, "username contains invalid characters");
        }

        //check if username is already taken
        Optional<InstructorProfile> existingProfile = instructorProfileRepository.findByUsername(username);
        if(existingProfile.isPresent()){
            return new GenericResult(false, "username is already taken");
        }

        if (biography == null || biography.isEmpty()) {
            return new GenericResult(false, "biography cannot be empty");
        }
        if (specialization == null || specialization.isEmpty()) {
            return new GenericResult(false, "specialization cannot be empty");
        }

        // Handle files: pfp, id, cert(s)
        List<String> uploadedMaterialRefs = new ArrayList<>();

        try {
            if (data.pfp != null && !data.pfp.isEmpty()) {
                MaterialRequests.CreateRequest req = new MaterialRequests.CreateRequest(data.pfp.getContentType(), data.pfp.getOriginalFilename(), data.pfp);
                MaterialResults.CreateResult res = materialService.uploadFile(req);
                if (res.success) {
                    uploadedMaterialRefs.add("pfp:" + res.data.id);
                }
            }

            if (data.id != null && !data.id.isEmpty()) {
                MaterialRequests.CreateRequest req = new MaterialRequests.CreateRequest(data.id.getContentType(), data.id.getOriginalFilename(), data.id);
                MaterialResults.CreateResult res = materialService.uploadFile(req);
                if (res.success) {
                    uploadedMaterialRefs.add("id:" + res.data.id);
                }
            }

            if (data.cert != null) {
                for (MultipartFile c : data.cert) {
                    if (c != null && !c.isEmpty()) {
                        MaterialRequests.CreateRequest req = new MaterialRequests.CreateRequest(c.getContentType(), c.getOriginalFilename(), c);
                        MaterialResults.CreateResult res = materialService.uploadFile(req);
                        if (res.success) {
                            uploadedMaterialRefs.add("cert:" + res.data.id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new GenericResult(false, "failed to upload attached files: " + e.getMessage());
        }

        // compose safely
        StringBuilder sb = new StringBuilder(512);
        sb.append("Username: ").append(username).append("\n")
                .append("Biography: ").append(biography).append("\n")
                .append("Specialization: ").append(specialization).append("\n");

        if(!uploadedMaterialRefs.isEmpty()){
            sb.append("Materials:\n");
            for(String r : uploadedMaterialRefs) {
                sb.append(r).append("\n");
            }
        }
        String content = sb.toString();

        Request req = new Request();
        req.setType("promoteInstructor");
        req.setSentByUserId(userId);
        req.setTitle("Promotion request");
        req.setContent(content);
        req.setReportedUserId(null);
        req.setTargetCourseId(null);
        // status defaults to "pending" per model; createdAt handled by DB
        Request saved = requestRepository.save(req);

        return new GenericResult(true, "request created with id " + saved.getReqId());
    }




}
