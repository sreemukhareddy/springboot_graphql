package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeBookDataSource;
import com.course.graphql.datasource.fake.FakeHelloDataSource;
import com.course.graphql.generated.types.SmartSearchResult;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakeSmartSearchResultDataResolver {
    @DgsData(
            parentType = "Query",
            field = "smartSearch"
    )
    public List<SmartSearchResult> getSmartSearch(@InputArgument(name = "keyword")Optional<String> keyword) {
        var smartSearchList = new ArrayList<SmartSearchResult>();

        if(keyword.isEmpty()) {
            smartSearchList.addAll(FakeBookDataSource.BOOK_LIST);
            smartSearchList.addAll(FakeHelloDataSource.HELLO_LIST);
        } else {
            var keywordToSearch = keyword.get();

            FakeHelloDataSource.HELLO_LIST.stream()
                    .filter(hello -> hello.getText().equalsIgnoreCase(keywordToSearch))
                    .forEach(smartSearchList::add);

            FakeBookDataSource.BOOK_LIST.stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(keywordToSearch))
                    .forEach(smartSearchList::add);

        }




        return smartSearchList;
    }

}
