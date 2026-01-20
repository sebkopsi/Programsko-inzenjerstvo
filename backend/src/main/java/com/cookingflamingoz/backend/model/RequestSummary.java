package com.cookingflamingoz.backend.model;

public interface RequestSummary {
    Integer getReqId();
    String getTitle();
    String getType();
    String getStatus();
    Integer getSentByUserId();
}