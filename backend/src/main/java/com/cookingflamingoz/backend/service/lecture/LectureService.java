package com.cookingflamingoz.backend.service.lecture;

import com.cookingflamingoz.backend.controller.lecture.LectureRequests;
import com.cookingflamingoz.backend.controller.material.MaterialRequests;
import com.cookingflamingoz.backend.model.*;
import com.cookingflamingoz.backend.repository.*;
import com.cookingflamingoz.backend.service.material.MaterialResults;
import com.cookingflamingoz.backend.service.material.MaterialService;
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
    private final MaterialService materialService;
    private final MaterialRepository materialRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository, UserRepository userRepository, ModuleRepository moduleRepository, CourseRepository courseRepository, DifficultyLevelRepository difficultyLevelRepository, MaterialService materialService, MaterialRepository materialRepository){
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
        this.difficultyLevelRepository = difficultyLevelRepository;
        this.materialService = materialService;
        this.materialRepository = materialRepository;
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

        if(!userId.equals(courseData.get().getCreator().getUserId())){
            return new LectureResults.CreateResult(false, "user is not owner of course", null);
        }

        var difficulty = difficultyLevelRepository.findByName(request.difficulty);
        if(difficulty.isEmpty()){
            return new LectureResults.CreateResult(false, "difficulty does not exist", null);
        }

        Integer vidId = null;
        if(request.videoType.equals("integrated")){
            if(request.video == null){
                return new LectureResults.CreateResult(false, "video is null but type is integrated", null);
            }
            var createVid = new MaterialRequests.CreateRequest("INTEGRATED", request.video.getName(), request.video);
            var result = materialService.uploadFile(createVid);
            if(!result.success){
                return new LectureResults.CreateResult(false, "failed to create video", null);
            }
            vidId = result.data.id;
        } else if(request.videoType.equals("external")){
            if(request.url.isEmpty()){
                return new LectureResults.CreateResult(false, "url is empty but type is integrated", null);
            }
            Material externalVid = new Material();
            externalVid.setName("external_video");
            externalVid.setContents(request.url);
            externalVid.setType("EXTERNAl");
            externalVid = materialRepository.save(externalVid);
            vidId = externalVid.getMaterialId();
        }


        var cookTime = Duration.parse(request.cookTime);
        var prepTime = Duration.parse(request.prepTime);

        Lecture newLecture = new Lecture();
        newLecture.setName(request.name);
        newLecture.setCookingTime(cookTime);
        newLecture.setPreparationTime(prepTime);
        newLecture.setCreatorId(userId);
        newLecture.setModuleId(request.moduleId);
        newLecture.setWrittenSteps(request.steps);
        newLecture.setDifficultyLevel(difficulty.get());
        newLecture.setQuizjson(request.quiz);
        newLecture.setMinScore(request.minScore);
        newLecture.setVideoId(vidId);

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
