package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    public Tag findByNameAndCategory(String name, String category);
    public Set<Tag> findByNameIn(Set<String> names);


    @Query(value =  "SELECT t.\"name\" AS tag, COUNT(ut.\"userId\") AS cnt " +
                    "FROM public.\"tag\" t "  +
                    "LEFT JOIN public.\"usertag\" ut ON ut.\"tagId\" = t.\"tagId\" " +
                    "GROUP BY t.\"name\"",
                    nativeQuery = true)
    List<Object[]> countUsersGroupedByTag();

    default Map<String, Long> countUsersByTag() {
        List<Object[]> rows = countUsersGroupedByTag();
        return rows.stream().collect(Collectors.toMap(
                r -> Objects.toString(r[0], "UNKNOWN"),
                r -> ((Number) r[1]).longValue()
        ));
    }

    @Query(value = """
            SELECT t."name" AS tag, COUNT(ct."courseId") AS cnt
            FROM public."tag" t
            LEFT JOIN public."courseTag" ct ON ct."tagId" = t."tagId"
            GROUP BY t."name"
        """, nativeQuery = true)
    List<Object[]> countCoursesGroupedByTag();

    default Map<String, Long> countCoursesByTag() {
        List<Object[]> rows = countCoursesGroupedByTag();
        return rows.stream().collect(Collectors.toMap(
                r -> Objects.toString(r[0], "UNKNOWN"),
                r -> ((Number) r[1]).longValue()
        ));
    }


}
