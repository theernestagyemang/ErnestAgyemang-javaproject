package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.ProductOrderServiceApplication;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.service.implementations.ProductServiceImpl;
import com.ernestagyemang.productorderservice.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

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
public class ProductControllerTest {
    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Autowired
    ProductServiceImpl productService;

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
                .variable("productInput", Map.of("name", "Apple", "stock", 10, "price", 100.0))
                .execute()
                .path("createProduct")
                .entity(Product.class)
                .satisfies(order -> {
                    assertNotNull(order.getId());
                });

    }

    @Test
    @Order(2)
    void testFindAllUsersShouldReturnAllProducts() throws NotFoundException {
        adminAuthenticate.authenticateAsAdmin();
        String document = """
                        query GetAllProducts{
                            getAllProducts {
                                id
                                name
                                stock
                                price
                            }
                        }
                """;

        httpGraphQlTester.document(document)
                .execute()
                .path("getAllProducts")
                .entityList(Product.class)
                .satisfies(Assertions::assertNotNull);
    }

    @Test
    @Order(3)
    void shouldUpdateExistingOrder() {
        adminAuthenticate.authenticateAsAdmin();

        String document = """
                    mutation UpdateProduct($productInput: ProductUpdateInput!){
                       updateProduct(productInput: $productInput) {
                            id
                            name
                            stock
                            price
                       }
                     }

                """;

        httpGraphQlTester.document(document)
                .variable("productInput", Map.of("id", 1, "name", "Apple", "stock", 20, "price", 150.0))
                .execute()
                .path("updateProduct")
                .entity(User.class);

    }

    //First Create and Product with id 1
    @Test
    @Order(4)
    void validIdShouldReturnProduct() throws NotFoundException {
        adminAuthenticate.authenticateAsAdmin();
        String document = """
                        query GetProductById{
                             getProductById(id: 1) {
                                id
                                name
                                stock
                                price
                             }
                         }
                """;

        httpGraphQlTester.document(document)
                .variable("id", 1)
                .execute()
                .path("getProductById")
                .entity(Product.class)
                .satisfies(product -> {
                    assertNotNull(product);
                    assertEquals(1L, product.getId());
                });
    }

    @Test
    @Order(5)
    void shouldRemoveProductWithValidId() {
        adminAuthenticate.authenticateAsAdmin();

        String document = """
                    mutation DeleteProduct($id: ID!) {
                        deleteProduct(id: $id)
                    }
                """;

        httpGraphQlTester.document(document)
                .variable("id", 1)
                .executeAndVerify();

    }

}
