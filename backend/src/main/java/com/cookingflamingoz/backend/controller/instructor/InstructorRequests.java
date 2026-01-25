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

    @AllArgsConstructor
    public static class PromotionRequestBody {
        public String username;
        public String biography;
        public String specialization;

        public Byte[] profilePicture;
        public Byte[] identificationDocument;
        public Byte[] diploma;
    };

}
