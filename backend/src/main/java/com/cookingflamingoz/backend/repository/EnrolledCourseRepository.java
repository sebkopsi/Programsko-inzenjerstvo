package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.EnrolledCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, EnrolledCourse.EnrolledCourseId> {

    @Query("""
    select distinct ec
    from EnrolledCourse ec
    join fetch ec.course c
    join fetch c.creator
    left join fetch c.tags t
    where ec.user.userId = :userId
    """)
    Set<EnrolledCourse> findByUserId(@Param ("userId") Integer userId);

}
