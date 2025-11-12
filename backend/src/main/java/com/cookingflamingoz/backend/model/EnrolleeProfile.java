package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;


@Entity
@Table(name = "\"enrolleeProfile\"", schema = "public") // keep quotes since table name is "user"
public class EnrolleeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"enrolleeId\"")
    private Integer enrolleeId;

    @Column(nullable = false)
    private String username;

    @Column(name = "\"skillLevelId\"", nullable = false)
    private Integer skillLevelId;


    //constructors
    public EnrolleeProfile() {} // needed for JPA

    public EnrolleeProfile(Integer enrolleeId, String username, Integer skillLevelId) {
        this.enrolleeId = enrolleeId;
        this.username = username;
        this.skillLevelId = skillLevelId;
    }


    //getters and setters

    public Integer getEnrolleeId() {
        return enrolleeId;
    }

    public void setEnrolleeId(Integer enrolleeId) {
        this.enrolleeId = enrolleeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSkillLevelId() {
        return skillLevelId;
    }

    public void setSkillLevelId(Integer skillLevelId) {
        this.skillLevelId = skillLevelId;
    }

    //toString
    @Override
    public String toString() {
        return "EnrolleeProfile{" +
                "enrolleeId=" + enrolleeId +
                ", username='" + username + '\'' +
                ", skillLevelId='" + skillLevelId + '\'';
    }
}

