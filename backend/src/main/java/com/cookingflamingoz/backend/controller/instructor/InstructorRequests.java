package com.cookingflamingoz.backend.controller.instructor;

import org.springframework.web.multipart.MultipartFile;

public class InstructorRequests {

    public static class SearchRequest {
        public String term;
        public String scope = "";
        public Integer userId;
    };

    // Plain POJO used with @ModelAttribute for multipart/form-data binding.
    public static class PromotionRequestBody {
        // textual fields (support both old and new names)
        public String username;       // older API name
        public String biography;      // older API name
        public String specialization; // older API name

        public String title; // frontend name for username/title
        public String desc;  // frontend name for biography/description

        // file fields - match frontend input names: pfp, id, cert
        public MultipartFile pfp;
        public MultipartFile id;
        public MultipartFile[] cert;

        public PromotionRequestBody() {}
    };

}
