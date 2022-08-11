package com.course.graphql.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GraphqlErrorResponse {

    @Data
    public static class Location {
        private int line;
        private int column;
    }

    private String message;
    private List<String> path;
    private List<GraphqlErrorResponse.Location> locations;
}
