# ErnestAgyemang-javaproject
A Java Spring Boot project with GraphQL API for managing orders, users, and products.


# ProductOrderService

ProductOrderService is a Java Spring Boot project with GraphQL API for managing orders, users, and products.

## Author

- **Author:** Ernest Agyemang

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Setup](#setup)
- [How to Run](#how-to-run)
- [Additional Information](#additional-information)

## Overview

ProductOrderService is a Java Spring Boot project designed to facilitate the management of orders, users, and products in a seamless and efficient manner. The primary goal of this application is to provide a robust backend system for handling various operations related to e-commerce or inventory management.

### Key Features

- **GraphQL API:** Utilizing GraphQL for a flexible and powerful API, allowing clients to request exactly the data they need.

- **User Authentication and Authorization:** Implementing Spring Security for secure authentication and authorization to ensure that only authorized users can perform specific actions.

- **Data Models:**
    - **User:** Managing user information, including name, email, and password.
    - **Product:** Handling product details such as name, stock, and price.
    - **Order:** Facilitating order creation with associated products and user information.
    - **ProductLine:** Representing individual product lines within an order, specifying the product, quantity, and order ID.

- **Low-Stock Tracking:** Implementing business logic to identify and display items that are running low on stock.

## Project Structure

The project is structured as follows:

```plaintext
com.ernestagyemang.productorderservice
├── config
├── api
├── dto
├── enums
├── exceptions
├── model
├── repository
└── service
    └── implementations
    └── interfaces
```


## Dependencies

List the main dependencies used in the project.

- Spring Boot
- Spring Data JPA
- H2 Database
- Spring Security
- GraphQL
- Lombok
- JUnit
- Mockito

## Setup

Instructions on how to set up the project locally.

1. Clone the repository:

   ```bash
   git clone https://github.com/theernestagyemang/ErnestAgyemang-javaproject.git

    cd ProductOrderService

    mvn clean install

    mvn spring-boot:run

    ```
Access the GraphQL endpoint:

   ```bash
   http://localhost:8080/graphql
   ```


Additional Information
Any additional information or notes about the project.

For development purposes, the H2 Database Console is accessible at http://localhost:8080/h2-console. Use JDBC URL jdbc:h2:mem:testdb, username h2, and no password.
