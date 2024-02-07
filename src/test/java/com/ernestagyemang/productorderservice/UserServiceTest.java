package com.ernestagyemang.productorderservice;

import com.ernestagyemang.productorderservice.dto.UserDto;
import com.ernestagyemang.productorderservice.enums.UserRole;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.repository.UserRepository;
import com.ernestagyemang.productorderservice.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser() {
        // Mocking data
        UserDto userInput = new UserDto(null,"John Doe", "john@example.com", "password123", "USER");

        // Mocking behavior
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);  // Assume saving generates an ID
            return savedUser;
        });

        // Perform the actual test
        User createdUser = userService.createUser(userInput);

        // Assertions
        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals("John Doe", createdUser.getName());
        Assertions.assertEquals("john@example.com", createdUser.getEmail());
        Assertions.assertEquals("password123", createdUser.getPassword());
        Assertions.assertEquals(UserRole.valueOf("USER"), createdUser.getRole());
        // Add more assertions based on your actual User entity fields

        // Verify that the save method was called once
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }
}

