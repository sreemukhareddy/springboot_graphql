package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeHelloDataSource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Hello;
import com.course.graphql.generated.types.HelloInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class FakeHelloMutation {

   // @DgsMutation(field = DgsConstants.MUTATION.AddHello)
    @DgsData(field = DgsConstants.MUTATION.AddHello, parentType = DgsConstants.MUTATION.TYPE_NAME)
    public Integer addHello(@InputArgument(name = "helloInput")HelloInput helloInput) {

        FakeHelloDataSource.HELLO_LIST.add(Hello.newBuilder()
                                                .randomNumber(helloInput.getNumber())
                .text(helloInput.getText())                .build()
        );

        return FakeHelloDataSource.HELLO_LIST.size();
    }

    @DgsData(field = DgsConstants.MUTATION.ReplaceHelloText, parentType = DgsConstants.MUTATION.TYPE_NAME)
    public List<Hello> replaceHello(@InputArgument(name = "helloInput")HelloInput helloInput) {

        FakeHelloDataSource.HELLO_LIST.stream().forEach(hello -> {
            if (hello.getRandomNumber() == helloInput.getNumber())
                hello.setText(helloInput.getText());
        });

        return FakeHelloDataSource.HELLO_LIST;
    }

    @DgsData(field = DgsConstants.MUTATION.DeleteHello, parentType = DgsConstants.MUTATION.TYPE_NAME)
    public Integer deleteHello(@InputArgument(name = "number")Integer number) {

        FakeHelloDataSource.HELLO_LIST.removeIf(hello -> hello.getRandomNumber() != number);

        return FakeHelloDataSource.HELLO_LIST.size();
    }
}
