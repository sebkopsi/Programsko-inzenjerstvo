package com.cookingflamingoz.backend.controller.module;

import com.cookingflamingoz.backend.service.module.ModuleResults;
import com.cookingflamingoz.backend.service.module.ModuleService;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/course/{courseId}/module")
public class ModuleController {

    @Autowired
    private JwtUtil jwtUtil;

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/{id}")
    public ModuleResults.GetByIdResult getById(@PathVariable Integer id) throws Exception {
        return moduleService.getById(id);
    }


    @PostMapping("")
    public ModuleResults.CreateResult create(@RequestBody ModuleRequests.CreateRequest request, @PathVariable Integer courseId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        request.courseId = courseId;
        return moduleService.create(userID, request);
    }

    @PostMapping("/search")
    public ModuleResults.SearchResult findAll(@RequestBody ModuleRequests.SearchRequest request, @PathVariable Integer courseId) {
        request.courseId = courseId;
        return moduleService.search(request);
    }
}