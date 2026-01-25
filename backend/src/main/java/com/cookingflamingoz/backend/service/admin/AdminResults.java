package com.cookingflamingoz.backend.service.admin;

import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.model.RequestSummary;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class AdminResults {

    public static class AdminSummaryInfo {
        public Integer reqId;
        public String title;
        public String type;
        public String status;
        public Integer sentByUserId;
        public Date createdAt;
        public String userEmail;

        public AdminSummaryInfo(String userEmail, Date createdAt, Integer sentByUserId, String status, String type, String title, Integer reqId) {
            this.userEmail = userEmail;
            this.createdAt = createdAt;
            this.sentByUserId = sentByUserId;
            this.status = status;
            this.type = type;
            this.title = title;
            this.reqId = reqId;
        }
    }

    // Za GetAll (Inbox/Search)
    public static class InboxResult extends GenericResult {
        public List<AdminSummaryInfo> data;

        public InboxResult(boolean success, String message, @Nullable List<AdminSummaryInfo> data) {
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