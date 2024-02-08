package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.UserInput;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.service.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @QueryMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public User getUserById(@Argument Long id){
        return userService.getUserById(id);
    }

    @MutationMapping
    public User createUser(@Argument UserInput userInput) {
        return userService.createUser(userInput);
    }

    @MutationMapping
    public User updateUser(@Argument UserInput userInput) {
        return userService.updateUser(userInput);
    }

    @MutationMapping
    public String deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }

}
