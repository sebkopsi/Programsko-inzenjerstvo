package com.cookingflamingoz.backend.service.admin;

import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.model.RequestSummary;
import com.cookingflamingoz.backend.util.GenericResult;
import jakarta.annotation.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    // for stats
    public static class StatisticResult extends GenericResult {
        public Integer numActiveUsers;
        public Integer numTotalUsers;
        public Integer numVerifiedUsers;
        public Map<String, Long> numUsersByDifficulty;
        public Map<String, Long> numUsersByTag;
        public Map<String, Long> numCoursesByTag;


        public StatisticResult(boolean success, String message,
                               Integer numActiveUsers,
                               Integer numTotalUsers,
                               Integer numVerifiedUsers,
                               Map<String, Long> numUsersByDifficulty,
                               Map<String, Long> numUsersByTag,
                               Map<String, Long> numCoursesByTag) {
            super(success, message);
            this.numActiveUsers = numActiveUsers;
            this.numTotalUsers = numTotalUsers;
            this.numVerifiedUsers = numVerifiedUsers;
            this.numUsersByDifficulty = numUsersByDifficulty;
            this.numUsersByTag = numUsersByTag;
            this.numCoursesByTag = numCoursesByTag;
        }


    }
}