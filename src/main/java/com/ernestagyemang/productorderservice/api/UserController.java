package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.UserInput;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.service.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @QueryMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @QueryMapping
    public User getUserById(@Argument Long id){
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @MutationMapping
    public User createUser(@Argument UserInput userInput) {
        return userService.createUser(userInput);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @MutationMapping
    public User updateUser(@Argument UserInput userInput, Principal principal) {
        return userService.updateUser(userInput, principal);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @MutationMapping
    public String deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }

}
