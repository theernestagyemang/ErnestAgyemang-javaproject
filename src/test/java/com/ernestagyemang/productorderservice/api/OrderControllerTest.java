package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.ProductOrderServiceApplication;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.service.implementations.OrderServiceImpl;
import com.ernestagyemang.productorderservice.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({UserServiceImpl.class})
@ContextConfiguration(classes = ProductOrderServiceApplication.class)
public class OrderControllerTest {
    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AdminAuthenticate adminAuthenticate;

    @Test
    @Order(1)
    void shouldCreateProduct() {
        adminAuthenticate.authenticateAsAdmin();
        String document = """
                                mutation CreateProduct($productInput: ProductInput!){
                                   createProduct(productInput: $productInput) {
                                        id
                                        name
                                        stock
                                        price
                                   }
                                 }
                """;
        AtomicReference<Product> newOrder = new AtomicReference<>();
        httpGraphQlTester.document(document)
                .variable("productInput", Map.of("name", "Apple", "stock", 10, "price", 10.0))
                .execute()
                .path("createProduct")
                .entity(Product.class)
                .satisfies(order -> {
                    assertNotNull(order.getId());
                });

    }

    @Test
    @Order(2)
    void shouldCreateProduct1() {
        adminAuthenticate.authenticateAsAdmin();

        String document = """
                                mutation CreateProduct($productInput: ProductInput!){
                                   createProduct(productInput: $productInput) {
                                        id
                                        name
                                        stock
                                        price
                                   }
                                 }
                """;
        AtomicReference<Product> newOrder = new AtomicReference<>();
        httpGraphQlTester.document(document)
                .variable("productInput", Map.of("name", "Orange", "stock", 20, "price", 5.0))
                .execute()
                .path("createProduct")
                .entity(Product.class)
                .satisfies(order -> {
                    assertNotNull(order.getId());
                });

    }

    @Test
    @Order(3)
    void shouldCreateProduct2() {
        adminAuthenticate.authenticateAsAdmin();

        String document = """
                                mutation CreateProduct($productInput: ProductInput!){
                                   createProduct(productInput: $productInput) {
                                        id
                                        name
                                        stock
                                        price
                                   }
                                 }
                """;
        AtomicReference<Product> newOrder = new AtomicReference<>();
        httpGraphQlTester.document(document)
                .variable("productInput", Map.of("name", "Banana", "stock", 30, "price", 3.0))
                .execute()
                .path("createProduct")
                .entity(Product.class)
                .satisfies(order -> {
                    assertNotNull(order.getId());
                });

    }

    @Test
    @Order(4)
    void shouldCreateOrder() {
        adminAuthenticate.authenticateAsAdmin();
        String document = """
                                mutation CreateOrder($orderInput: OrderInput!){
                                   createOrder(orderInput: $orderInput) {
                                     id
                                     productLineList {
                                       id
                                       product {
                                         id
                                         name
                                         stock
                                         price
                                       }
                                       quantity
                                     }
                                     user {
                                       id
                                       name
                                       email
                                     }
                                   }
                                 }
                """;
        AtomicReference<com.ernestagyemang.productorderservice.model.Order> newOrder = new AtomicReference<>();
        httpGraphQlTester.document(document)
                .variable("orderInput", Map.of(
                        "productLineInputList", List.of(
                                Map.of("productId", 1, "quantity", 3),
                                Map.of("productId", 2, "quantity", 5)
                        )
                ))
                .execute()
                .path("createOrder")
                .entity(com.ernestagyemang.productorderservice.model.Order.class)
                .satisfies(order -> {
                    assertNotNull(order.getId());
                });

    }

    @Test
    @Order(5)
    void testFindAllUsersShouldReturnAllOrders() throws NotFoundException {
        adminAuthenticate.authenticateAsAdmin();
        String document = """
                        query GetAllOrders{
                            getAllOrders {
                                id
                                productLineList {
                                    id
                                    product {
                                        id
                                        name
                                        stock
                                        price
                                    }
                                    quantity
                                }
                                user {
                                    id
                                    name
                                    email
                                }
                            }
                        }
                """;

        httpGraphQlTester.document(document)
                .execute()
                .path("getAllOrders")
                .entityList(com.ernestagyemang.productorderservice.model.Order.class)
                .satisfies(Assertions::assertNotNull);
    }

    @Test
    @Order(6)
    void shouldUpdateExistingOrder() {
        adminAuthenticate.authenticateAsAdmin();

        String document = """
                    mutation UpdateOrder($orderInput: OrderUpdateInput!){
                       updateOrder(orderInput: $orderInput) {
                         id
                         productLineList {
                           id
                           product {
                             id
                             name
                             stock
                             price
                           }
                           quantity
                         }
                         user {
                           id
                           name
                           email
                         }
                       }
                     }

                """;

        httpGraphQlTester.document(document)
                .variable("orderInput", Map.of(
                        "id", 1,
                        "productLineInputList", List.of(
                                Map.of("id", 1, "productId", 1, "quantity", 5),
                                Map.of("id", 3, "productId", 3, "quantity", 2)
                        )
                ))
                .execute()
                .path("updateOrder")
                .entity(User.class);

    }

    //First Create and Order with id 1
    @Test
    @Order(7)
    void validIdShouldReturnOrder() throws NotFoundException {
        adminAuthenticate.authenticateAsAdmin();

        String document = """
                        query GetOrderById{
                             getOrderById(id: 1) {
                                 id
                                 productLineList {
                                     id
                                     product {
                                         id
                                         name
                                         stock
                                         price
                                     }
                                     quantity
                                 }
                                 user {
                                     id
                                     name
                                     email
                                 }
                             }
                         }
                """;

        httpGraphQlTester.document(document)
                .variable("id", 1)
                .execute()
                .path("getOrderById")
                .entity(com.ernestagyemang.productorderservice.model.Order.class)
                .satisfies(order -> {
                    assertNotNull(order);
                    assertEquals(1L, order.getId());
                });
    }

    @Test
    @Order(8)
    void shouldRemoveOrderWithValidId() {
        adminAuthenticate.authenticateAsAdmin();

        String document = """
                    mutation DeleteOrder($id: ID!) {
                        deleteOrder(id: $id)
                    }
                """;

        httpGraphQlTester.document(document)
                .variable("id", 1)
                .executeAndVerify();

    }

}
