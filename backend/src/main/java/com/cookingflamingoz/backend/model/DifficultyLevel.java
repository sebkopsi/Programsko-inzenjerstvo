package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;


@Entity
@Table(name = "\"difficultyLevel\"", schema = "public") // keep quotes since table name is "difficultyLevel"
public class DifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"difficultyId\"")
    private Integer difficultyId;

    @Column(nullable = false)
    private String name;


    //constructors
    public DifficultyLevel() {} // needed for JPA

    public DifficultyLevel(Integer diffidultyId, String name) {
        this.difficultyId = diffidultyId;
        this.name = name;
    }
    //getters and setters


    public Integer getDiffidultyId() {
        return difficultyId;
    }

    public void setDiffidultyId(Integer diffidultyId) {
        this.difficultyId = diffidultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //toString
    @Override
    public String toString() {
        return "DifficultyLevel{" +
                "diffidultyId=" + difficultyId +
                ", name='" + name + '\'' +
                '}';
    }
}

