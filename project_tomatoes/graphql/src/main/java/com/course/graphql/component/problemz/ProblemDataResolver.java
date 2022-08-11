package com.course.graphql.component.problemz;

import com.course.graphql.exception.ProblemzAuthenticationException;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.ProblemCreateInput;
import com.course.graphql.generated.types.ProblemResponse;
import com.course.graphql.service.command.ProblemzCommandService;
import com.course.graphql.service.query.ProblemzQueryService;
import com.course.graphql.service.query.UserzQueryService;
import com.course.graphql.util.GraphQlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ProblemDataResolver {

    @Autowired
    private ProblemzQueryService queryService;

    @Autowired
    private ProblemzCommandService problemzCommandService;

    @Autowired
    private UserzQueryService userzQueryService;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ProblemLatestList)
    public List<Problem> getProblemLatestList() {
        return queryService.problemzLatestList()
                .stream()
                .map(GraphQlBeanMapper::mapToGraphQl)
                .collect(Collectors.toList());
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ProblemDetail)
    public Problem getProblemDetail(@InputArgument(name = "id") String problemId) {
        var problemz = queryService.problemzDetail(problemId).orElseThrow(DgsEntityNotFoundException::new);
        return GraphQlBeanMapper.mapToGraphQl(problemz);
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.ProblemCreate)
    public ProblemResponse createProblem(@RequestHeader(name = "authToken", required = true) String authtoken,
                                         @InputArgument(name = "problem")ProblemCreateInput problemCreateInput) {
        var userz = userzQueryService.findUserzByAuthToken(authtoken)
                .orElseThrow(ProblemzAuthenticationException::new);
        var problemz = GraphQlBeanMapper.mapToEntity(problemCreateInput, userz);
        var created = problemzCommandService.createProblem(problemz);
        return ProblemResponse.newBuilder()
                .problem(GraphQlBeanMapper.mapToGraphQl(created))
                .build();
    }

    @DgsData(parentType = DgsConstants.SUBSCRIPTION.TYPE_NAME, field = DgsConstants.SUBSCRIPTION.ProblemAdded)
    public Flux<Problem> subscribeProblemAdded() {
        return problemzCommandService.problemzFlux()
                .map(GraphQlBeanMapper::mapToGraphQl);
    }
}
