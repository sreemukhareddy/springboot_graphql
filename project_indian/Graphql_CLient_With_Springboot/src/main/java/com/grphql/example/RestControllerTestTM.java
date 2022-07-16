package com.grphql.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;

@RestController
@RequestMapping("/test")
public class RestControllerTestTM {
	
	@Autowired
	private GraphQLWebClient graphQLWebClient;

	
	@GetMapping("/get/{id}")
	public StudentResponse getStudentResponse(@PathVariable Long id) {
		
		System.out.println("came here man");
		
		String query = "query {\n"
				+ "  student(id: " + id + ") {\n"
				+ "    id\n"
				+ "    firstName\n"
				+ "    lastName\n"
				+ "    email\n"
				+ "    street\n"
				+ "    city\n"
				+ "    learningSubjects(nameFilter: ALL) {\n"
				+ "      id\n"
				+ "      subjectName\n"
				+ "      marksObtained\n"
				+ "    }\n"
				+ "    fullName\n"
				+ "  }\n"
				+ "}";
		
		GraphQLRequest graphQLRequest = GraphQLRequest.builder()
		                                              .query(query)
		                                              .build();
		
		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest)
		                .block();
		
		StudentResponse studentResponse = graphQLResponse.get("student", StudentResponse.class);
		
		return studentResponse;
	}
	
	@GetMapping("/get/{id}/variable")
	public StudentResponse getStudentResponseWithGraphqlVariable(@PathVariable Long id) {
		
		System.out.println("came here man");
		
		String query = "query ($id : Long) {\n"
				+ "  student(id: $id) {\n"
				+ "    id\n"
				+ "    firstName\n"
				+ "    lastName\n"
				+ "    email\n"
				+ "    street\n"
				+ "    city\n"
				+ "    learningSubjects(nameFilter: ALL) {\n"
				+ "      id\n"
				+ "      subjectName\n"
				+ "      marksObtained\n"
				+ "    }\n"
				+ "    fullName\n"
				+ "  }\n"
				+ "}";
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("id", id);
		
		GraphQLRequest graphQLRequest = GraphQLRequest.builder()
		                                              .query(query)
		                                              .variables(variables)
		                                              .build();
		
		GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest)
		                .block();
		
		StudentResponse studentResponse = graphQLResponse.get("student", StudentResponse.class);
		
		return studentResponse;
	}
	
	@PostMapping("/save")
	public StudentResponse saveStudent(@RequestBody CreateStudentRequest request) {
		
		
		String mutation = "mutation {\n"
				+ "  createStudent(createStudentRequest: {\n"
				+ "    \n"
				+ "    city : \"city-test\"\n"
				+ "    firstName : \"firstname-test\"\n"
				+ "    lastName : \"lastname-test\"\n"
				+ "    email : \"email-test\"\n"
				+ "    subjectsLearning : [\n"
				+ "      {\n"
				+ "        subjectName : \"xyzabc\"\n"
				+ "        marksObtained : 120\n"
				+ "      }\n"
				+ "    ]\n"
				+ "    \n"
				+ "  }) {\n"
				+ "    id\n"
				+ "    firstName\n"
				+ "    lastName\n"
				+ "    email\n"
				+ "    street\n"
				+ "    city\n"
				+ "    learningSubjects(nameFilter: ALL) {\n"
				+ "      id\n"
				+ "      subjectName\n"
				+ "      marksObtained\n"
				+ "    }\n"
				+ "    fullName\n"
				+ "  }\n"
				+ "}" ;
		
		 GraphQLRequest graphQLRequest = GraphQLRequest.builder()
		               .query(mutation)
		               .build();
		 
		 GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
		 StudentResponse studentResponse = graphQLResponse.get("student", StudentResponse.class);
		
		return studentResponse;
	}
	
	@PostMapping("/save/variable")
	public StudentResponse saveStudentVariable(@RequestBody CreateStudentRequest request) {
		
		
		String mutation = "mutation createStudent ($createStudentRequest : CreateStudentRequest ) {\n"
				+ "  createStudent(createStudentRequest : $createStudentRequest"
				+ "  ) {\n"
				+ "    id\n"
				+ "    firstName\n"
				+ "    lastName\n"
				+ "    email\n"
				+ "    street\n"
				+ "    city\n"
				+ "    learningSubjects(nameFilter: ALL) {\n"
				+ "      id\n"
				+ "      subjectName\n"
				+ "      marksObtained\n"
				+ "    }\n"
				+ "    fullName\n"
				+ "  }\n"
				+ "}" ;
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("createStudentRequest", request);
		
		 GraphQLRequest graphQLRequest = GraphQLRequest.builder()
		               .query(mutation)
		               .variables(variables)
		               .build();
		 
		 GraphQLResponse graphQLResponse = graphQLWebClient.post(graphQLRequest).block();
		 StudentResponse studentResponse = graphQLResponse.get("createStudent", StudentResponse.class);
		
		return studentResponse;
	}

}
