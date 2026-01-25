package com.cookingflamingoz.backend.controller.course;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class CourseRequests {
    @AllArgsConstructor
    public static class SearchRequest {
        public String term;
        public String scope = "";
        public Integer userId;
    };

    @AllArgsConstructor
    public static class CreateRequest {
        public String name;
        public String desc;
        public Set<String> tags;
    }

    @AllArgsConstructor
    public static class UpdateRequest {
        // fields are optional; service will apply non-null / non-empty values
        public String name;
        public String desc;
        public Set<String> tags;
    }
}
