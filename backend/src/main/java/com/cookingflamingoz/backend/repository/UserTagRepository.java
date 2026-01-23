package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Tag;
import com.cookingflamingoz.backend.model.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface UserTagRepository extends JpaRepository<UserTag, Integer> {
    public Set<UserTag> findByPreferred(boolean preferred);
    public Set<UserTag> findByUserId(Integer userId);
}
