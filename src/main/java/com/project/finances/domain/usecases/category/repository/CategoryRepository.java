package com.project.finances.domain.usecases.category.repository;

import com.project.finances.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("select c from Category c join c.user u " +
            "where u.id = :userId")
    List<Category> findCategoryByUserId(String userId);
}
