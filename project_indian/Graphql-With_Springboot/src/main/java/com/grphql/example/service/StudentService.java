package com.grphql.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grphql.example.entity.Address;
import com.grphql.example.entity.Student;
import com.grphql.example.entity.Subject;
import com.grphql.example.model.CreateStudentRequest;
import com.grphql.example.model.CreateSubjectRequest;
import com.grphql.example.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	private static final Map<Long, Student> students = new HashMap<>();

	@PostConstruct
	public void load() {

		Address s1a = new Address(123L, "street-1", "city-1", null);

		Student s1 = new Student(123L, "first-name", "last-name", "email", s1a, null);
		s1.setAddress(s1a);
		s1a.setStudent(s1);

		Subject s1s1 = new Subject(123L, "s1", 12d, s1);
		Subject s1s2 = new Subject(123L, "s2", 122d, s1);

		s1.setLearningSubjects(Arrays.asList(s1s1, s1s2));

		students.put(123l, s1);

	}

	public Student getStudentById(long id) {
		// return studentRepository.findById(id).get();

		return students.get(id);
	}

	public Student createStudent(CreateStudentRequest createStudentRequest) {
		Student student = new Student();

		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());

		Address address = new Address();
		address.setStreet(createStudentRequest.getStreet());
		address.setCity(createStudentRequest.getCity());

		student.setAddress(address);
		long nextLong = new Random().nextLong();
		

		List<Subject> subjectsList = new ArrayList<Subject>();

		if (createStudentRequest.getSubjectsLearning() != null) {
			for (CreateSubjectRequest createSubjectRequest : createStudentRequest.getSubjectsLearning()) {
				Subject subject = new Subject();
				subject.setSubjectName(createSubjectRequest.getSubjectName());
				subject.setMarksObtained(createSubjectRequest.getMarksObtained());
				subject.setStudent(student);
				System.out.println("CAME_HERE");
				subjectsList.add(subject);
			}
		}
		
		
		
		student.setLearningSubjects(subjectsList);
		student.setId(nextLong);
		
		students.put(nextLong, student);
		
		return student;
	}
}
