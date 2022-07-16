package com.grphql.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grphql.example.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
