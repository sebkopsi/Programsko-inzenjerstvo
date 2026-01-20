package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "request", schema = "public")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"reqId\"")
    private Integer reqId;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(nullable = false)
    private String type;

    @Column(name = "\"sentByUserId\"", nullable = false)
    private Integer sentByUserId;

    @Column(name = "\"reportedUserId\"")
    private Integer reportedUserId;

    @Column(name = "\"targetCourseId\"")
    private Integer targetCourseId;

    @Column(nullable = false)
    private String status = "pending";

    @Column(name = "\"createdAt\"", insertable = false, updatable = false)
    private Date createdAt;

    // Defaultni konstruktor potreban za JPA
    public Request() {}
}
