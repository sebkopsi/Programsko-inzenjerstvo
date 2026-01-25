package com.cookingflamingoz.backend.controller.material;

import com.cookingflamingoz.backend.service.material.MaterialResults;
import com.cookingflamingoz.backend.service.material.MaterialService;
import com.cookingflamingoz.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private JwtUtil jwtUtil;

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Resource> getById(@PathVariable Integer id) throws Exception {
        Resource file = materialService.getById(id);
        if(!file.exists()){
            return ResponseEntity.internalServerError().build();
        }

        
        return ResponseEntity.ok().body(file);
    }

    @GetMapping("/{id}/info")
    public MaterialResults.GetByIdResult getByIdInfo(@PathVariable Integer id) throws Exception {
        return materialService.getByIdInfo(id);
    }


    @PostMapping(value = "",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MaterialResults.CreateResult create(@ModelAttribute MaterialRequests.CreateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userID = Integer.parseInt(authentication.getPrincipal().toString());
        return materialService.uploadFile(request);
    }

}