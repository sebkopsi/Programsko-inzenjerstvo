package com.cookingflamingoz.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "\"lecture\"", schema = "public")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"lectureId\"")
    private Integer lectureId;

    @Column(name = "\"name\"", nullable = false)
    private String name;

    @Column(name = "\"writtenSteps\"", columnDefinition = "TEXT",  nullable = false)
    private String writtenSteps;

    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    @Column(
            name = "\"preparationTime\"",
            columnDefinition = "interval", nullable = false
    )
    private Duration preparationTime;

    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    @Column(
            name = "\"cookingTime\"",
            columnDefinition = "interval", nullable = false
    )
    private Duration cookingTime;

    @Column(name = "\"moduleId\"", nullable = false)
    private Integer moduleId;

    @Column(name = "\"videoId\"", nullable = true)
    private Integer videoId;

    @Column(name = "\"creatorId\"", nullable = false)
    private Integer creatorId;

    @Column(name = "\"minscore\"", nullable = true)
    private Integer minScore;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"difficultyId\"", nullable = false)
    private DifficultyLevel difficultyLevel;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Quiz quizjson;
}
