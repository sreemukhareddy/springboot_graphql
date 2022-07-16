package com.grphql.example.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.grphql.example.entity.Student;
import com.grphql.example.model.CreateStudentRequest;
import com.grphql.example.response.StudentResponse;
import com.grphql.example.service.StudentService;

@Service
public class Mutation implements GraphQLMutationResolver {
	
	@Autowired
	private StudentService studentService;
	

	public StudentResponse createStudent (CreateStudentRequest createStudentRequest) {
		Student student = studentService.createStudent(createStudentRequest);
		return new StudentResponse(student);
	}
}
