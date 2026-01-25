package com.cookingflamingoz.backend.service.lecture;

import com.cookingflamingoz.backend.model.Lecture;
import com.cookingflamingoz.backend.model.Quiz;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.annotation.Nullable;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LectureResults {
    public static class LectureInfo {
        public String name;
        public Integer id;
        public String cookTime;
        public String prepTime;
        public String writtenSteps;
        public String difficulty;
        public Quiz quiz;
        public Integer videoId;

        LectureInfo(String name, Integer id){
            this.name = name;
            this.id = id;
        }
    }

    public static class TagInfo {
        public String name;
        public String category;

        TagInfo(String name, String category){
            this.category = category;
            this.name = name;
        }
    }
    public static class GetByIdResult extends GenericResult {
        public LectureInfo data;

        public GetByIdResult(boolean success, String message, Lecture lecture) {
            super(success, message);
            if(lecture != null ){
               this.data =  new LectureInfo(
                        lecture.getName(),
                        lecture.getLectureId()
                );
                data.videoId = lecture.getVideoId();
               data.cookTime = String.format("%02dm %02ds", lecture.getCookingTime().toHours() % 24,
                       lecture.getCookingTime().toMinutes() % 60);;
               data.prepTime = String.format("%02dm %02ds", lecture.getPreparationTime().toHours() % 24,
                       lecture.getPreparationTime().toMinutes() % 60);;
               data.writtenSteps = lecture.getWrittenSteps();
               data.difficulty = lecture.getDifficultyLevel().getName();
               data.quiz = lecture.getQuizjson();
            }
        }
    }

    public static class SearchResult extends GenericResult {
        public List<LectureInfo> data;
        public SearchResult(boolean success, String message, @Nullable Set<Lecture> data) {
            super(success, message);
            if (data != null) {
                this.data = data.stream().map(lecture -> {
                    var lecResult = new LectureInfo(
                        lecture.getName(),
                       lecture.getLectureId()
                );
                    lecResult.cookTime = String.format("%02dh %02dm", lecture.getCookingTime().toHours() % 24,
                            lecture.getCookingTime().toMinutes() % 60);;
                    lecResult.prepTime = String.format("%02dh %02dm", lecture.getPreparationTime().toHours() % 24,
                            lecture.getPreparationTime().toMinutes() % 60);;
                    lecResult.writtenSteps = lecture.getWrittenSteps();
                    lecResult.difficulty = lecture.getDifficultyLevel().getName();
                return lecResult;
                }).collect(Collectors.toList());
            }

        }
    }

    public static class CreateResult extends GenericResult {
        public Integer id;

        public CreateResult(boolean success, String message, @Nullable Lecture lecture) {
            super(success, message);
            if(lecture != null){
                this.id = lecture.getLectureId();
            }
        }
    }

}
