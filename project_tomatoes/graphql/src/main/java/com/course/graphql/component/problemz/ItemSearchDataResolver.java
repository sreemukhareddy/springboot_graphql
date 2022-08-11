package com.course.graphql.component.problemz;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.SearchItemFilter;
import com.course.graphql.generated.types.SearchableItem;
import com.course.graphql.service.query.ProblemzQueryService;
import com.course.graphql.service.query.SolutionzQuery;
import com.course.graphql.util.GraphQlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ItemSearchDataResolver {

    @Autowired
    private ProblemzQueryService problemzQueryService;

    @Autowired
    private SolutionzQuery solutionzQueryService;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ItemSearch)
    public List<SearchableItem> searchItems(@InputArgument(name = "filter")SearchItemFilter filter) {
        var result = new ArrayList<SearchableItem>();
        var keyword = filter.getKeyword();

        var problemzByKeyword = problemzQueryService.problemzByKeyword(keyword)
                .stream()
                .map(GraphQlBeanMapper::mapToGraphQl)
                .collect(Collectors.toList());

        result.addAll(problemzByKeyword);

        var solutionzByKeyword = solutionzQueryService.solutionzByKeyword(keyword)
                .stream()
                .map(GraphQlBeanMapper::mapToGraphQl)
                .collect(Collectors.toList());

        result.addAll(solutionzByKeyword);

        if(result.isEmpty()) {
            throw  new DgsEntityNotFoundException("Entity/Entities not found for " + keyword);
        }

        result.sort(Comparator.comparing(SearchableItem::getCreateDateTime).reversed());

        return result;
    }
}
