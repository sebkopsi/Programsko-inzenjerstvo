package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "course", schema = "public") // keep quotes since table name is "user"
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"courseId\"")
    private Integer courseId;

    @Column(nullable = false)
    private String name;

    @Column(name = "\"creatorId\"", nullable = false)
    private Integer creatorId;

    @Column(name = "\"description\"", nullable = false)
    private String description;

    @Column(name = "\"difficultyId\"")
    private Integer difficultyId;

    @Column(name = "\"avgRating\"", nullable = false)
    private double avgRating = 0.00; // match DB default;


    //constructors
    public Course() {} // needed for JPA

    public Course(Integer id, String name, Integer creatorId, String description, Integer difficultyId, Float avgRating) {
        this.courseId= id;
        this.name = name;
        this.creatorId = creatorId;
        this.description = description;
        this.difficultyId = difficultyId;
        this.avgRating = avgRating;
    }

    //getters and setters
    public Integer getCourseId() { return courseId; }

    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getCreatorId() { return creatorId; }

    public void setCreatorId(Integer creatorId) { this.creatorId = creatorId; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getDifficultyId() { return difficultyId; }

    public void setDifficultyId(Integer difficultyId) { this.difficultyId = difficultyId; }

    public double getAvgRating() { return avgRating; }

    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }

    //toString method
    @Override
    public String toString() {
        return "Course{" +
                "id=" + courseId +
                ", name=" + name +
                ", creatorId=" + creatorId +
                ", description=" + description +
                ", difficultyId=" + difficultyId +
                ", avgRating=" + avgRating +
                '}';
    }
}