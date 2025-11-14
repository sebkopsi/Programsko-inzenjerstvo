package com.cookingflamingoz.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserTagId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "\"userId\"")
    private User user;


    @ManyToOne
    @JoinColumn(name = "\"tagId\"")
    private Tag tag;


    public UserTagId(User user, Tag tag) {
        this.user = user;
        this.tag = tag;
    }

    public UserTagId() {

    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
