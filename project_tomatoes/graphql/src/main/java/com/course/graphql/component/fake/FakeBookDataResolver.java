package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeBookDataSource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Book;
import com.course.graphql.generated.types.ReleaseHistory;
import com.course.graphql.generated.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DgsComponent
public class FakeBookDataResolver {

    @DgsData(parentType = "Query", field = "books")
    public List<Book> booksWrittenBy(@InputArgument(name = "author") String authorName) {
        if(StringUtils.isBlank(authorName)) {
            return FakeBookDataSource.BOOK_LIST;
        }
        return FakeBookDataSource.BOOK_LIST.stream().filter(
                book -> {
                    return book.getAuthor().getName().equalsIgnoreCase(authorName);
                }
        ).collect(Collectors.toList());
    }

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.BooksByReleased
    )
    public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {

        var releaseMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");

        var releaedInput = ReleaseHistoryInput.newBuilder()
                .printedEdition((boolean)releaseMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                .year((int)releaseMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                .build();


        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> this.matchReleaseHistory(releaedInput, b.getReleased()))
                .collect(Collectors.toList());

    }

    private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory element) {
        return input.getPrintedEdition().equals(element.getPrintedEdition())
                && input.getYear() == element.getYear();
    }


}
