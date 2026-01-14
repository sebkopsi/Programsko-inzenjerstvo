package com.cookingflamingoz.backend.controller.module;

import java.util.Set;

public class ModuleRequests {
    public static class SearchRequest {
        public String term;
        public String scope;
        public Integer courseId;
    }

    public static class CreateRequest {
        public String name;
        public Integer courseId;
        public Integer order;
    }
}
