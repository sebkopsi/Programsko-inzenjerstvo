package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyLevelRepository
        extends JpaRepository<DifficultyLevel, Integer>{

    DifficultyLevel getByName(String beginner);
}


