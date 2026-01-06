package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.CourseTag;
import com.cookingflamingoz.backend.model.CourseTagId;
import com.cookingflamingoz.backend.model.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface CourseTagRepository extends JpaRepository<CourseTag, Integer> {
}
