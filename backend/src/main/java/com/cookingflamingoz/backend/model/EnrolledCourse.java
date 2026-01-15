package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "\"enrolledCourse\"", schema = "public")
@IdClass(EnrolledCourse.EnrolledCourseId.class)
public class EnrolledCourse {

    public  static class EnrolledCourseId implements Serializable {
        private Integer course;
        private Integer user;


        public EnrolledCourseId(Integer course, Integer user) {
            this.course = course;
            this.user = user;
        }

        public EnrolledCourseId() {
        }
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"courseId\"", nullable = false)
    private Course course;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"userId\"", nullable = false)
    private User user;

    @Column(name = "completionPercentage", nullable = false)
    private Integer completionPercentage = 0;

    @Column(name = "certificateId", nullable = true)
    private Integer certificateId;

    //constructors
    public EnrolledCourse() {} // needed for JPA

}

