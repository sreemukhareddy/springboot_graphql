package com.grphql.example.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.grphql.example.model.SampleRequest;
import com.grphql.example.response.StudentResponse;
import com.grphql.example.service.StudentService;

@Component
public class Query implements GraphQLQueryResolver {
	
	@Autowired
	private StudentService studentService;

	public String firstQuery() {
		return "First Query";
	}
	
	public String secondQuery() {
		return "Second Query";
	}
	
	
	
	public String fullName(SampleRequest sampleRequest) {
		return sampleRequest.getFirstName() + " " + sampleRequest.getLastName();
	}
	
	public StudentResponse student (long id) {
		return new StudentResponse(studentService.getStudentById(id));
	}

}
