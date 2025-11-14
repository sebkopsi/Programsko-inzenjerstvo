package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Integer> {
    public Tag findByNameAndCategory(String name, String category);
}
