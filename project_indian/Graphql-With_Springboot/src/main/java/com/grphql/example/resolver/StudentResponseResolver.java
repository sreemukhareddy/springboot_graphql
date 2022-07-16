package com.grphql.example.resolver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.grphql.example.enem.filter.SubjectNameFilter;
import com.grphql.example.entity.Subject;
import com.grphql.example.response.StudentResponse;
import com.grphql.example.response.SubjectResponse;

@Service
public class StudentResponseResolver implements GraphQLResolver<StudentResponse> {

	public List<SubjectResponse> getLearningSubjects(StudentResponse studentResponse, SubjectNameFilter nameFilter) {

		List<SubjectResponse> learningSubjects = null;

		if (studentResponse.getStudent().getLearningSubjects() != null) {
			learningSubjects = new ArrayList<SubjectResponse>();
			for (Subject subject : studentResponse.getStudent().getLearningSubjects()) {
				if (nameFilter.name().equalsIgnoreCase(subject.getSubjectName()))
					learningSubjects.add(new SubjectResponse(subject));
				if(nameFilter.name().equalsIgnoreCase(SubjectNameFilter.ALL.name())) {
					learningSubjects.add(new SubjectResponse(subject));
				}
			}
		}
		return learningSubjects;
	}

	public String getFullName(StudentResponse studentResponse) {
		return studentResponse.getFirstName() + " " + studentResponse.getLastName();
	}
}
