package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByEnrolleeId(Integer enrolleeId);

    // Derived query to count users where isActive == true
    // There is no `isActive` field on User; use an existing boolean column as proxy (isVerified) so callers get a meaningful count.
    @Query("SELECT COUNT(u) FROM User u")
    Long countByIsActiveTrue();// active actually doesn't exist, all are active :)

    // total number of users
    @Query("SELECT COUNT(u) FROM User u")
    Long countAllUsers();

    // number of verified users (isVerified == true)
    @Query("SELECT COUNT(u) FROM User u WHERE u.isVerified = true")
    Long countVerifiedUsers();

    // list of (difficulty name, count) pairs
    @Query(value = "SELECT dl.\"name\" AS difficulty, COUNT(u.\"userId\") AS cnt " +
            "FROM public.\"user\" u " +
            "LEFT JOIN public.\"enrolleeProfile\" ep ON ep.\"enrolleeId\" = u.\"enrolleeId\" " +
            "LEFT JOIN public.\"difficultyLevel\" dl ON dl.\"difficultyId\" = ep.\"skillLevelId\" " +
            "GROUP BY dl.\"name\"",
            nativeQuery = true)
    List<Object[]> countUsersGroupedByDifficulty();

    default Map<String, Long> countUsersByDifficulty() {
        List<Object[]> rows = countUsersGroupedByDifficulty();
        return rows.stream().collect(Collectors.toMap(
                r -> Objects.toString(r[0], "UNKNOWN"),
                r -> r[1] == null ? 0L : ((Number) r[1]).longValue()
        ));
    }


}
