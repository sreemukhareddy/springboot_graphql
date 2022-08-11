package com.course.graphql.client;

import com.course.graphql.client.response.FilmResponse;
import com.course.graphql.client.response.PlanetResponse;
import com.course.graphql.client.response.StarshipResponse;
import com.course.graphql.generated.types.Film;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StarwarsGraphQlClient {
    private static final String URL = "https://swapi-graphql.netlify.app/.netlify/functions/index";

    private static final String QUERY = """
            query allPlanets {
              allPlanets {
                planets {
                  name
                  climates
                  terrains
                }
              }
            }
            query oneStarshipFixed {
              starship(id: "c3RhcnNoaXBzOjU=") {
                model
                name
                manufacturers
              }
            }
            query oneFilm($filmId: ID!) {
              film(filmID: $filmId) {
                title
                director
                releaseDate
              }
            }
            """;

    RestTemplate restTemplate = new RestTemplate();



    private GraphQLResponse getGraphQlResponse(String operationName, Map<String, ? extends Object> variableMap,
                                               Map<String, List<String>> headerMap) {

        if (variableMap == null ) {
            variableMap = new HashMap<>();
        }

        RequestExecutor requestExecutor   = (url, headers, body) -> {
            HttpHeaders requestHeaders = new HttpHeaders();
            headers.forEach(requestHeaders::addAll);
            if (headerMap != null) {
                headerMap.forEach(requestHeaders::addAll);
            }
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, requestHeaders),String.class);
            return new HttpResponse(responseEntity.getStatusCodeValue(), responseEntity.getBody());
        };

        GraphQLClient graphQLClient = new CustomGraphQLClient(URL, requestExecutor);


        return graphQLClient.executeQuery(QUERY, variableMap, operationName);
    }

    public String asJson(String operationName, Map<String, Object> variablesMap, Map<String, List<String>> headersMap){
        return getGraphQlResponse(operationName, variablesMap, headersMap).getJson();
    }

    public List<PlanetResponse> allPlanets() {
        return getGraphQlResponse("allPlanets", null, null)
                .extractValueAsObject("allPlanets.planets", new TypeRef<List<PlanetResponse>>() {
                })
                ;
    }

    public StarshipResponse oneStarshipFixed() {
        return getGraphQlResponse("oneStarshipFixed", null, null)
                .extractValueAsObject("starship", new TypeRef<StarshipResponse>() {
                })
                ;
    }

    public FilmResponse oneFilm(String filmId) {
        var variablesMap = Map.of("filmId", filmId);
        return getGraphQlResponse("oneFilm", variablesMap, null)
                .extractValueAsObject("film", FilmResponse.class)
                ;
    }

    public List<GraphQLError> oneFilmInvalid() {
        var variablesMap = Map.of("filmId", "xxxx");
        var graphQlResponse = getGraphQlResponse("oneFilm", variablesMap, null);
        return graphQlResponse.hasErrors() ? graphQlResponse.getErrors() : null;
    }

    public static void main(String[] args) {
        System.out.println(new StarwarsGraphQlClient().oneStarshipFixed());
    }




    private void forMyPurpose_1(){
                WebSocketGraphQLClient webSocketGraphQLClient = new WebSocketGraphQLClient("");
                webSocketGraphQLClient.reactiveExecuteQuery("", Collections.emptyMap())
                        .doOnError(error -> {
                            error.printStackTrace();
                        })
                        .doOnNext(value -> {
                            System.out.println(value.getJson());
                        })
                        .map(graphQLResponse -> {
                            if (graphQLResponse.hasErrors() ) {
                                throw new RuntimeException("We got an error from here...!!!");
                            }
                            return graphQLResponse.getJson();
                        })
                        .subscribe();
    }

    private void forMyPurpose_2() {
        RestTemplate restTemplate = new RestTemplate();
        RequestExecutor requestExecutor = (url, headers, body) -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            headers.forEach(httpHeaders::addAll);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, httpHeaders),String.class);
            return new HttpResponse(exchange.getStatusCodeValue(), exchange.getBody());
        };
        GraphQLClient graphQLClient = new CustomGraphQLClient(URL, requestExecutor);
        var graphQLResponse = graphQLClient.executeQuery(QUERY);
        graphQLResponse.getJson()
                .toString();
    }

    private void forMyPurpose_3() {

    }
}
