package com.cookingflamingoz.backend.service.course;

import com.cookingflamingoz.backend.model.Course;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.annotation.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

public class CourseResults {
    public static class CourseInfo {
        public String name;
        public String desc;
        public Integer creatorId;
        public Integer id;
        public Set<TagInfo> tags;

        CourseInfo(String name, String desc, Integer creatorId, Integer id, Set<TagInfo> tags){
            this.creatorId = creatorId;
            this.name = name;
            this.desc = desc;
            this.tags = tags;
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

    public static class SearchResult extends GenericResult {
        public Set<CourseInfo> data;
        public SearchResult(boolean success, String message, @Nullable Set<Course> data) {
            super(success, message);
            if (data != null) {
                this.data = data.stream().map(course -> {
                    Set<TagInfo> tags = course.getTags().stream().map(tag -> {return new TagInfo(tag.getName(), tag.getCategory());}).collect(Collectors.toSet());
                    return new CourseInfo(
                        course.getName(),
                        course.getDescription(),
                        course.getCreator().getId(),
                       course.getCourseId(),
                       tags
                );}).collect(Collectors.toSet());
            }
        }
    }

    public static class CreateResult extends GenericResult {
        public Integer id;

        public CreateResult(boolean success, String message, @Nullable Course course) {
            super(success, message);
            if(course != null){
                this.id = course.getCourseId();
            }
        }
    }

}
