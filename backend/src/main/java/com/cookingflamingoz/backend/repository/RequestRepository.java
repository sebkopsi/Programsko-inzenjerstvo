package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Request;
import com.cookingflamingoz.backend.model.RequestSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<RequestSummary> findAllProjectedBy();

    List<Request> findAllBySentByUserId(Integer sentByUserId);

}