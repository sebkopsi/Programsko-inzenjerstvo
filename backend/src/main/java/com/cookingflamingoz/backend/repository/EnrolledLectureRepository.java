package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.EnrolledLecture;
import com.cookingflamingoz.backend.model.EnrolledLecture.EnrolledLectureId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolledLectureRepository
        extends JpaRepository<EnrolledLecture, EnrolledLectureId> {
}