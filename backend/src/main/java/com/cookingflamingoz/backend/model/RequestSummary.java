package com.cookingflamingoz.backend.model;

import java.util.Date;

public interface RequestSummary {
    Integer getReqId();
    String getTitle();
    String getType();
    String getStatus();
    Integer getSentByUserId();
    Date getCreatedAt();
}