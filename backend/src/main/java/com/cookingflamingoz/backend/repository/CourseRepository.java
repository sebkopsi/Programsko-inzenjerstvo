package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Course;
import com.cookingflamingoz.backend.model.EnrolleeProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CourseRepository
        extends JpaRepository<Course, Integer> {

    Course findByName(String name);

    @Query("""
        select distinct c from Course c
        where  (lower(c.name) || lower(c.description)) like lower(concat('%', :term, '%'))
    """)
    List<Course> search(@Param("term") String term);
}
