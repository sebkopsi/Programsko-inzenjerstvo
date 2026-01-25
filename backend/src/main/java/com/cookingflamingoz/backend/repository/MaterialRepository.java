package com.cookingflamingoz.backend.repository;

import com.cookingflamingoz.backend.model.Material;
import com.cookingflamingoz.backend.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository
        extends JpaRepository<Material, Integer> {

}
