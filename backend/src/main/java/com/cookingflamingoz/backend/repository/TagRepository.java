package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface TagRepository extends JpaRepository<Tag, Integer> {
    public Tag findByNameAndCategory(String name, String category);
    public Set<Tag> findByNameIn(Set<String> names);
}
