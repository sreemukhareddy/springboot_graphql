package com.course.graphql.client.request;

import lombok.Data;

import java.util.Map;

@Data
public class GraphqlRestRequest {

    private String query;
    private Map<String, ? extends Object> variables;
}
