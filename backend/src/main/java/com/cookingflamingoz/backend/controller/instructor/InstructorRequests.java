package com.cookingflamingoz.backend.controller.instructor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InstructorRequests {

    @AllArgsConstructor
    public static class SearchRequest {
        public String term;
        public String scope = "";
        public Integer userId;
    };

}
