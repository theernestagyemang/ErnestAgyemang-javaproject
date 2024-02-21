package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.ProductOrderServiceApplication;
import com.ernestagyemang.productorderservice.enums.UserRole;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static graphql.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({UserServiceImpl.class})
@ContextConfiguration(classes = ProductOrderServiceApplication.class)
class UserControllerTest {

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AdminAuthenticate adminAuthenticate;

    @Test
    @Order(1)
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void testFindAllUsersShouldReturnAllUsers() throws NotFoundException {
        String document = """
                query {
                    getAllUsers {
                        id
                        name
                        email
                        password
                        role
                    }
                }
                """;

        httpGraphQlTester.document(document)
                .execute()
                .path("getAllUsers")
                .entityList(User.class)
                .satisfies(Assertions::assertNotNull);
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void validIdShouldReturnUser() throws NotFoundException {
        String document = """
                query GetUserById($id: ID!){
                    getUserById(id: $id) {
                        id
                        name
                        email
                        password
                        role
                    }
                }
                """;

        httpGraphQlTester.document(document)
                .variable("id", 1)
                .execute()
                .path("getUserById")
                .entity(User.class)
                .satisfies(user -> {
                    assertNotNull(user);
                    assertEquals(1L, user.getId());
                });
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldCreateUser() throws NotFoundException {
        int currentUserCount = userService.getAllUsers().size();

        String document = """
                    mutation CreateUser($userInput: UserInput!){
                        createUser(userInput: $userInput) {
                            id
                            name
                            email
                            password
                            role
                        }
                    }
                """;
        AtomicReference<User> newUser = new AtomicReference<>();
        httpGraphQlTester.document(document)
                .variable("userInput", Map.of("name", "Ernest1", "email", "ea@gmail.com", "password", "password", "role", "ROLE_USER"))
                .execute()
                .path("createUser")
                .entity(User.class)
                .satisfies(user -> {
                    assertNotNull(user.getId());
                    assertEquals("Ernest1", user.getName());
                    assertEquals("ea@gmail.com", user.getEmail());
                    assertEquals(UserRole.ROLE_USER, user.getRole());
                    newUser.set(user);
                });

        User createdUser = userService.getUserById(newUser.get().getId());
        assertTrue(passwordEncoder.matches("password", createdUser.getPassword()));
        assertEquals(currentUserCount + 1, userService.getAllUsers().size());
    }

    @Test
    @Order(4)
    void shouldUpdateExistingUser() throws NotFoundException {
        adminAuthenticate.authenticateAsAdmin();
        String document = """
                    mutation updateUser ($userInput: UserUpdateInput!){
                      updateUser(userInput: $userInput){
                        id
                        name
                        email
                        password
                        role
                      }
                    }
                """;

        httpGraphQlTester.document(document)
                .variable("userInput", Map.of("id", 1, "name", "Administrator", "email", "admin@admin.com", "password", "admin", "role", "ROLE_ADMIN"))
                .execute()
                .path("updateUser")
                .entity(User.class);

        User updatedUser = userService.getUserById(1L);
        assertEquals("Administrator", updatedUser.getName());
        assertEquals("admin@admin.com", updatedUser.getEmail());
        assertTrue(passwordEncoder.matches("admin", updatedUser.getPassword()));
        assertEquals(UserRole.ROLE_ADMIN, updatedUser.getRole());
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldRemoveUserWithValidId() throws NotFoundException {
        int currentCount = userService.getAllUsers().size();

        String document = """
                    mutation DeleteUser($id: ID!) {
                        deleteUser(id: $id)
                    }
                """;

        httpGraphQlTester.document(document)
                .variable("id", 1)
                .executeAndVerify();

        assertEquals(currentCount - 1, userService.getAllUsers().size());
    }
}
