package com.cookingflamingoz.backend.service.instructor;

import com.cookingflamingoz.backend.model.Course;
import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.service.course.CourseResults;
import com.cookingflamingoz.backend.util.GenericResult;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InstructorResult {

    public static class GetListByIdResult extends GenericResult {
        public List<Request> data;

        public GetListByIdResult(boolean success, String message, List<Request> allRequests) {
            super(success, message);
            this.data = allRequests;
        }
    }
}
