package com.dariosrest.restexample.repository;

import com.dariosrest.restexample.model.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Integer> {
}
