package com.cookingflamingoz.backend.controller.course;

import java.util.Set;

public class CourseRequests {
    public static class SearchRequest {
        public String term;
        public String scope;
    }

    public static class CreateRequest {
        public String name;
        public String desc;
        public Set<String> tags;
    }
}
