package com.ardb.spring_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ardb.spring_test.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findById(int id);
}
