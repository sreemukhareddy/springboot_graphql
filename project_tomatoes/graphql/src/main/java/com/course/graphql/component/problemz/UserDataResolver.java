package com.course.graphql.component.problemz;

import com.course.graphql.exception.ProblemzAuthenticationException;
import com.course.graphql.exception.ProblemzPermissionException;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.*;
import com.course.graphql.service.command.UserzCommandService;
import com.course.graphql.service.query.UserzQueryService;
import com.course.graphql.util.GraphQlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
public class UserDataResolver {

    @Autowired
    private UserzCommandService userzCommandService;

    @Autowired
    private UserzQueryService userzQueryService;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Me)
    public User accountInfo(@RequestHeader(name = "authToken", required = true) String authToken ) {

        var userz = userzQueryService.findUserzByAuthToken(authToken).orElseThrow(DgsEntityNotFoundException::new);

        return GraphQlBeanMapper.mapToGraphQl(userz);
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserCreate)
    public UserResponse createUser(@InputArgument(name = "user")UserCreateInput userCreateInput,
                                   @RequestHeader(value = "authToken", required = true) String authToken
    ) {

        var adminUserz = userzQueryService.findUserzByAuthToken(authToken)
                .orElseThrow(ProblemzAuthenticationException::new);

        if (!adminUserz.getUserRole().equalsIgnoreCase("ROLE_ADMIN")) {
            throw new ProblemzPermissionException();
        }

        var userz = GraphQlBeanMapper.mapToEntity(userCreateInput);
        var saved = userzCommandService.createUserz(userz);
        var userResponse = UserResponse
                .newBuilder()
                .user(GraphQlBeanMapper.mapToGraphQl(saved))
                .build();
        return userResponse;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserLogin)
    public UserResponse userLogin(@InputArgument(name = "user")UserLoginInput userLoginInput) {
        var generatedToken = userzCommandService.login(userLoginInput.getUsername(), userLoginInput.getPassword());
        var userAuthToken = GraphQlBeanMapper.mapToGraphQl(generatedToken);
        var userInfo = accountInfo(userAuthToken.getAuthToken());
        var userResponse = UserResponse
                .newBuilder()
                .authToken(userAuthToken)
                .user(userInfo)
                .build();
        return userResponse;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserActivation)
    public UserActivationResponse userActivation(@InputArgument(name = "user")UserActivationInput userActivationInput,
        @RequestHeader(value = "authToken", required = true) String authToken
    ) {

        var adminUserz = userzQueryService.findUserzByAuthToken(authToken)
                .orElseThrow(ProblemzAuthenticationException::new);

        if (!adminUserz.getUserRole().equalsIgnoreCase("ROLE_ADMIN")) {
            throw new ProblemzPermissionException();
        }

        var userz = userzCommandService.activateUser(userActivationInput.getUsername(), userActivationInput.getActive())
                .orElseThrow(DgsEntityNotFoundException::new);

        return UserActivationResponse.newBuilder()
                .isActive(userz.isActive())
                .build();

    }
}
