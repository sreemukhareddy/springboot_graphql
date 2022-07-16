package com.grphql.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grphql.example.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
}
