package com.cookingflamingoz.backend.service.module;

import com.cookingflamingoz.backend.controller.module.ModuleRequests;
import com.cookingflamingoz.backend.model.Module;
import com.cookingflamingoz.backend.model.Tag;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.CourseRepository;
import com.cookingflamingoz.backend.repository.ModuleRepository;
import com.cookingflamingoz.backend.repository.TagRepository;
import com.cookingflamingoz.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ModuleService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final ModuleRepository moduleRepository;
    private final TagRepository tagRepository;

    public ModuleService(ModuleRepository moduleRepository, TagRepository tagRepository, UserRepository userRepository, CourseRepository courseRepository){
        this.moduleRepository = moduleRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public com.cookingflamingoz.backend.service.module.ModuleResults.GetByIdResult getById(int id){
        Optional<Module> module =  moduleRepository.findById(id);
        return module.map(value -> new com.cookingflamingoz.backend.service.module.ModuleResults.GetByIdResult(true, "", value)).orElseGet(() -> new com.cookingflamingoz.backend.service.module.ModuleResults.GetByIdResult(false, "module not found", null));
    }

    public com.cookingflamingoz.backend.service.module.ModuleResults.SearchResult search(ModuleRequests.SearchRequest request) {
        Set<Module> data = new HashSet<>(moduleRepository.search(request.term, request.courseId));
        return new com.cookingflamingoz.backend.service.module.ModuleResults.SearchResult(true, "", data);
    }

    public com.cookingflamingoz.backend.service.module.ModuleResults.CreateResult create(Integer userId, ModuleRequests.CreateRequest request){

        User user = userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;
        if(user == null) {
            return new com.cookingflamingoz.backend.service.module.ModuleResults.CreateResult(false, "user does not exist", null);
        }

        var courseR = courseRepository.findById(request.courseId);
        if(courseR.isEmpty()){
            return new com.cookingflamingoz.backend.service.module.ModuleResults.CreateResult(false, "parent course does not exist", null);
        }
        var course = courseR.get();

        if(!userId.equals(course.getCreator().getUserId())){
            return new com.cookingflamingoz.backend.service.module.ModuleResults.CreateResult(false, "user does not own course", null);
        }

        Module newModule = new Module();
        newModule.setName(request.name);
        newModule.setCourse(course);
        newModule = moduleRepository.save(newModule);


        return new com.cookingflamingoz.backend.service.module.ModuleResults.CreateResult(true, "", newModule);
    }
}
