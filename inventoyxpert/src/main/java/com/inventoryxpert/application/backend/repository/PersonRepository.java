package com.inventoryxpert.application.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventoryxpert.application.backend.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}