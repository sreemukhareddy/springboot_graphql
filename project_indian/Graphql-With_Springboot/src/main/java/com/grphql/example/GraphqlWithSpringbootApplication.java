package com.grphql.example;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.grphql.example.entity.Address;
import com.grphql.example.entity.Student;
import com.grphql.example.entity.Subject;
import com.grphql.example.repository.AddressRepository;
import com.grphql.example.repository.StudentRepository;
import com.grphql.example.repository.SubjectRepository;

@SpringBootApplication
public class GraphqlWithSpringbootApplication implements CommandLineRunner{
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;

	public static void main(String[] args) {
		SpringApplication.run(GraphqlWithSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*
		 * Address s1a = new Address(null, "street-1", "city-1", null);
		 * 
		 * Student s1 = new Student(null, "first-name", "last-name", "email", s1a,
		 * null); s1.setAddress(s1a); s1a.setStudent(s1);
		 * 
		 * Subject s1s1 = new Subject(null, "s1", 12d, s1); Subject s1s2 = new
		 * Subject(null, "s2", 122d, s1);
		 * 
		 * s1.setLearningSubjects(Arrays.asList(s1s1, s1s2));
		 * 
		 * addressRepository.save(s1a); //studentRepository.save(s1);
		 * 
		 * subjectRepository.save(s1s1); subjectRepository.save(s1s2);
		 */
	}

}
