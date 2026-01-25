package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"material\"", schema = "public")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"materialId\"")
    private Integer materialId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String contents;


    @Column(nullable = false)
    private String name;
}
