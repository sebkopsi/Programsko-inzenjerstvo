package com.cookingflamingoz.backend.service.lecture;

import com.cookingflamingoz.backend.controller.lecture.LectureRequests;
import com.cookingflamingoz.backend.model.Lecture;
import com.cookingflamingoz.backend.model.Tag;
import com.cookingflamingoz.backend.model.User;
import com.cookingflamingoz.backend.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LectureService {
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final DifficultyLevelRepository difficultyLevelRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository, UserRepository userRepository, ModuleRepository moduleRepository, CourseRepository courseRepository, DifficultyLevelRepository difficultyLevelRepository){
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
        this.difficultyLevelRepository = difficultyLevelRepository;
    }

    public LectureResults.GetByIdResult getById(int id){
        Optional<Lecture> lecture =  lectureRepository.findById(id);
        return lecture.map(value -> new LectureResults.GetByIdResult(true, "", value)).orElseGet(() -> new LectureResults.GetByIdResult(false, "lecture not found", null));
    }

    public LectureResults.SearchResult search(LectureRequests.SearchRequest request) {
        Set<Lecture> data = new HashSet<>(lectureRepository.search(request.term));
        if(request.moduleId != null){
            data = data.stream().filter(lecture -> lecture.getModuleId().equals(request.moduleId)).collect(Collectors.toSet());
        }
        return new LectureResults.SearchResult(true, request.term, data);
    }

    public LectureResults.CreateResult create(Integer userId, LectureRequests.CreateRequest request){
        User user = userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;
        if(user == null) {
            return new LectureResults.CreateResult(false, "user does not exist", null);
        }
        

        var moduleData = moduleRepository.findById(request.moduleId);
        if(moduleData.isEmpty()){
            return new LectureResults.CreateResult(false, "module does not exist", null);
        }

        var courseData = courseRepository.findById(moduleData.get().getCourse().getCourseId());
        if (courseData.isEmpty()){
            return new LectureResults.CreateResult(false, "course does not exist", null);
        }

        if(!userId.equals(courseData.get().getCourseId())){
            return new LectureResults.CreateResult(false, "user is not owner of course", null);
        }



        Lecture newLecture = new Lecture();
        newLecture.setName(request.name);
        newLecture.setCookingTime(Duration.ofMinutes(0));
        newLecture.setPreparationTime(Duration.ofMinutes(0));
        newLecture.setCreatorId(userId);
        newLecture.setModuleId(request.moduleId);
        newLecture.setWrittenSteps("");
        newLecture.setDifficultyLevel(difficultyLevelRepository.getByName("beginner"));
        newLecture = lectureRepository.save(newLecture);
        Lecture finalNewLecture = newLecture;
//        Set<LectureTag> lectureTags = tags.stream().map(tag -> {
//            LectureTag lectureTag = new LectureTag();
//            lectureTag.setLecture(finalNewLecture);
//            lectureTag.setTag(tag);
//            return lectureTag;
//        }).collect(Collectors.toSet());
//
//        lectureTagRepository.saveAll(lectureTags);

        return new LectureResults.CreateResult(true, "", finalNewLecture);
    }
}
