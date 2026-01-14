package com.cookingflamingoz.backend.controller.lecture;

import com.cookingflamingoz.backend.service.lecture.LectureService;
import com.cookingflamingoz.backend.service.lecture.LectureResults;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/course/{courseId}/module/{moduleId}/lecture")
public class LectureController {

    @Autowired
    private JwtUtil jwtUtil;

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/{id}")
    public LectureResults.GetByIdResult getById(@PathVariable Integer id) {
        return lectureService.getById(id);
    }


    @PostMapping("")
    public LectureResults.CreateResult create(@RequestBody com.cookingflamingoz.backend.controller.lecture.LectureRequests.CreateRequest request, @PathVariable Integer moduleId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        request.moduleId=moduleId;
        return lectureService.create(userID, request);
    }

    @PostMapping("/search")
    public LectureResults.SearchResult findAll(@RequestBody com.cookingflamingoz.backend.controller.lecture.LectureRequests.SearchRequest request) {
        return lectureService.search(request);
    }
}