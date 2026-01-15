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
}
