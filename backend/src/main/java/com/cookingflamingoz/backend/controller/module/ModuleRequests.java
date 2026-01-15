package com.cookingflamingoz.backend.controller.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ModuleRequests {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SearchRequest {
        public String term;
        public String scope;
        public Integer courseId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CreateRequest {
        public String name;
        public Integer courseId;
        public Integer order;
    }
}
