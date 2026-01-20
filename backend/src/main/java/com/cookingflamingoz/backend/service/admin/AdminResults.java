package com.cookingflamingoz.backend.service.admin;

import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.model.RequestSummary;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.annotation.Nullable;

import java.util.List;

public class AdminResults {

    // Za GetAll (Inbox/Search)
    public static class InboxResult extends GenericResult {
        public List<RequestSummary> data;

        public InboxResult(boolean success, String message, @Nullable List<RequestSummary> data) {
            super(success, message);
            this.data = data;
        }
    }

    // Za GetById (Detalji)
    public static class DetailsResult extends GenericResult {
        public Request data;

        public DetailsResult(boolean success, String message, @Nullable Request request) {
            super(success, message);
            this.data = request;
        }
    }
}