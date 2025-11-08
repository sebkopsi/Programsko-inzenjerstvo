package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.EnrolleeProfile;
import com.cookingflamingoz.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolleeProfileRepository
        extends JpaRepository<EnrolleeProfile, Integer> {

    EnrolleeProfile findByUsername(String username);


}
