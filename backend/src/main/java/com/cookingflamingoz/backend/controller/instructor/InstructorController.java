package com.cookingflamingoz.backend.controller.instructor;



import com.cookingflamingoz.backend.controller.course.CourseRequests;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.InstructorProfileRepository;
import com.cookingflamingoz.backend.service.course.CourseResults;
import com.cookingflamingoz.backend.service.course.CourseService;
import com.cookingflamingoz.backend.service.instructor.InstructorResult;
import com.cookingflamingoz.backend.service.instructor.InstructorService;
import com.cookingflamingoz.backend.util.GenericResult;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private JwtUtil jwtUtil;

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/promotionRequest")
    public InstructorResult.GetListByIdResult getByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        instructorService.getRequestByUserId(userID);
        return instructorService.getRequestByUserId(userID);
    }



    @PostMapping("/promotionRequest")
    public GenericResult postPromotionRequest(@RequestBody InstructorRequests.PromotionRequestBody body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return instructorService.createPromotionRequest(userID, body);
    }


}
