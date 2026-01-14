package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Course;
import com.cookingflamingoz.backend.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureRepository
        extends JpaRepository<Lecture, Integer> {

    Lecture findByName(String name);

    @Query("""
        select c from Lecture c
        where  (lower(c.name)) like lower(concat('%', :term, '%'))
        order by c.lectureId asc 
    """)
    List<Lecture> search(@Param("term") String term);
}
