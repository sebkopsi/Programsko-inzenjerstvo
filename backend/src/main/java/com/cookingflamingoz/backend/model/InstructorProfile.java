package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "\"instructorProfile\"", schema = "public") // keep quotes since table name is "user"
public class InstructorProfile {


    //getters and setters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"instructorId\"")
    private Integer instructorId;


    @Column(nullable = false)
    private String username;

    @Column(name = "\"biography\"", nullable = false)
    private String biography;

    @Column(name = "\"specialization\"", nullable = false)
    private String specialization;

    //constructors
    public InstructorProfile() {} // needed for JPA

    public InstructorProfile(String username, String biography, String specialization) {
        this.username = username;
        this.biography = biography;
        this.specialization = specialization;
    }

    //toString
    @Override
    public String toString() {
        return "InstructorProfile{" +
                "instructorId=" + instructorId +
                ", username='" + username + '\'' +
                ", biography='" + biography + '\''+
                ", specialization='" + specialization + '\'';
    }

}

