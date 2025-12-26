package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findByName(String name);

    Course findByCreatorId(Integer creatorId);


    //@Query("SELECT u FROM course u WHERE u.name = :name")
    //Course findByName(@Param("name") String name);
}
