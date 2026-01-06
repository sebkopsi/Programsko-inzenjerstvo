package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "tag", schema = "public")
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"tagId\"")
    private Integer tagId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;



    public Tag(String name, String category) {
       this.name = name;
       this.category = category;
    }

    public Tag(Integer tagId, String name, String Category) {
        this.tagId = tagId;
        this.name = name;
        this.category = Category;
    }

    public Tag() {

    }

    public Integer getTagId() {
        return tagId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
