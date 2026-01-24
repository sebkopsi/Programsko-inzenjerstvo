package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;

@Entity
@Table(name = "\"enrolledLecture\"")
@Getter
@Setter
@NoArgsConstructor
public class EnrolledLecture {

    @EmbeddedId
    private EnrolledLectureId id;

    @Column(name = "\"completionPercentage\"", nullable = false)
    private Integer completionPercentage;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "\"quizanswers\"", columnDefinition = "json", nullable = false)
    private String quizanswers = "{}";
    
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class EnrolledLectureId implements Serializable {

        @Column(name = "\"lectureId\"", nullable = false)
        private Integer lectureId;

        @Column(name = "\"userId\"", nullable = false)
        private Integer userId;

        public EnrolledLectureId(Integer lectureId, Integer userId) {
            this.lectureId = lectureId;
            this.userId = userId;
        }
    }
}