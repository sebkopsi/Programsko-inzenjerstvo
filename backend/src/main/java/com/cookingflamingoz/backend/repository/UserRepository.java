package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByEnrolleeId(Integer enrolleeId);


    //@Query("SELECT u FROM User u WHERE u.email = :email")
    //User findByEmail(@Param("email") String email);
}
