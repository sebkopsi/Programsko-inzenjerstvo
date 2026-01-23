package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Tag;
import com.cookingflamingoz.backend.model.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface UserTagRepository extends JpaRepository<UserTag, Integer> {
    public Set<UserTag> findByPreferred(boolean preferred);

    @Query("select ut from UserTag ut where ut.primaryKey.user.userId = :userId")
    Set<UserTag> findAllByUserId(@Param("userId") Integer userId);
}
