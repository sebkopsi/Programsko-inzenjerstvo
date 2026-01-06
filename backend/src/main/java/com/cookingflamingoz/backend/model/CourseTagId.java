package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;

import java.io.Serializable;


public class CourseTagId implements Serializable {
    private Integer course;
    private Integer tag;


    public CourseTagId(Integer course, Integer tag) {
        this.course = course;
        this.tag = tag;
    }

    public CourseTagId() {

    }
}
