package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.EnrolleeProfile;
import com.cookingflamingoz.backend.model.InstructorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorProfileRepository
        extends JpaRepository<InstructorProfile, Integer> {
    InstructorProfile findByUsername(String username);
}

