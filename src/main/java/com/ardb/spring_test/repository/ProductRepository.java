package com.ardb.spring_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ardb.spring_test.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}