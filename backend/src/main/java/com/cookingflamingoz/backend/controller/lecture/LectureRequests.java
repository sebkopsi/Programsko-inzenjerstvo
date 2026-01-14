package com.cookingflamingoz.backend.controller.lecture;

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
    }
}
