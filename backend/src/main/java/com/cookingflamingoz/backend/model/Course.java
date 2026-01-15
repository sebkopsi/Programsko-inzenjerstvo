package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "\"course\"", schema = "public")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"courseId\"")
    private Integer courseId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"creatorId\"")
    private User creator;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseTag> tags;

    public Course() {

    }

    @Transient
    public Set<Tag> getTags(){
        return tags.stream().map(CourseTag::getTag).collect(Collectors.toSet());
    }

    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private Set<Module> modules;
}

