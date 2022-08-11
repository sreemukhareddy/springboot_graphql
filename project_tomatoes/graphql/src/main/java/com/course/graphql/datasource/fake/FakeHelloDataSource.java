package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Hello;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
public class FakeHelloDataSource {

    @Autowired
    private Faker faker;

    public static final List<Hello> HELLO_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        IntStream
                .rangeClosed(0, 19)
                .forEach( i -> {
                   var hello =  Hello.newBuilder()
                            .randomNumber(faker.random().nextInt(5000))
                            .text(faker.company().name())
                            .build();

                   HELLO_LIST.add(hello);
                });
    }

}
