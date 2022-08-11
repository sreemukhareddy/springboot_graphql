package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeHelloDataSource;
import com.course.graphql.generated.types.Hello;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@DgsComponent
public class FakeHelloDataResolver {

    @Autowired
    private FakeHelloDataSource fakeHelloDataSource;

    @DgsQuery
    public List<Hello> allHellos() {
        return FakeHelloDataSource.HELLO_LIST;
    }

    @DgsQuery
    public Hello oneHello() {
        return FakeHelloDataSource.HELLO_LIST.get(ThreadLocalRandom.current().nextInt(FakeHelloDataSource.HELLO_LIST.size()));
    }


}
