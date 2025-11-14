package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usertag")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user",
                joinColumns = @JoinColumn(name = "\"userId\"")),
        @AssociationOverride(name = "primaryKey.tag",
                joinColumns = @JoinColumn(name = "\"tagId\"")) })
public class UserTag  {
    @EmbeddedId
    private UserTagId primaryKey;

    @Column(nullable = false)
    private boolean preferred;

    public UserTag(boolean preferred, User user, Tag tag) {
        this.preferred = preferred;
        this.primaryKey = new UserTagId(user, tag);
    }

    public UserTag() {

    }

    public UserTagId getPrimaryKey() {
        return primaryKey;
    }
    public void setPrimaryKey(UserTagId primaryKey) {
        this.primaryKey = primaryKey;
    }


    @Transient
    public User getUser() {
        return getPrimaryKey().getUser();
    }

    public void setUser(User user) {
        getPrimaryKey().setUser(user);
    }

    @Transient
    public Tag getTag() {
        return getPrimaryKey().getTag();
    }

    public void setTag(Tag tag) {
        getPrimaryKey().setTag(tag);
    }


    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }
}
