package com.dariosrest.restexample.repository;

import com.dariosrest.restexample.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByUsersId(Integer userId);
}
