package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.EnrolledCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Integer> {

    @Query("select ut from EnrolledCourse ut where ut.user.userId = :userId")
    public Set<EnrolledCourse> findAllByUserId(@Param("userId") Integer userId);

}
