package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DifficultyLevelRepository
        extends JpaRepository<DifficultyLevel, Integer>{

    DifficultyLevel getByName(String name);

    Optional<DifficultyLevel> findByName(String name);
}


