package com.cookingflamingoz.backend.controller.lecture;

import com.cookingflamingoz.backend.model.Quiz;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.Set;

public class LectureRequests {
    public static class SearchRequest {
        public String term;
        public String scope;
        public Integer moduleId;
    }

    public static class CreateRequest {
        public String name;
        public Integer moduleId;
        public String prepTime;
        public String cookTime;
        public String difficulty;
        public String steps;
        public Integer minScore;
        public Quiz quiz;
        public String videoType;
        public MultipartFile video;
        public String url;
    }
}
