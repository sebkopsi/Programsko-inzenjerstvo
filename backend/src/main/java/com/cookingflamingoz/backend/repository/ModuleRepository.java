package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cookingflamingoz.backend.model.Module;

import java.util.List;

public interface ModuleRepository
        extends JpaRepository<Module, Integer> {

    Module findByName(String name);

    @Query("""
        select distinct c from Module c
        where  (lower(c.name)) like lower(concat('%', :term, '%')) and c.course.courseId = :courseId
        order by c.moduleId asc
    """)
    List<Module> search(@Param("term") String term, @Param("courseId") Integer courseId);
}
