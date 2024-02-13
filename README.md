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
- [GraphIQL Test Queries Sample](#graphIQL-test-queries-sample)

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

## Setup

Instructions on how to set up the project locally.
### Clone and run the repository:

```bash
git clone https://github.com/theernestagyemang/ErnestAgyemang-javaproject.git

cd ProductOrderService

mvn clean install

mvn spring-boot:run

```
    
### Build the Jar file
```bash
    mvn clean install
```    

## Run the Application with Docker

To run the application with Docker, follow these steps:

### Build the Docker Image
```bash
    docker build --tag=productorderservice:latest .
```

### Run the Docker Container
```bash
    docker run -p 8080:8080 productorderservice:latest
```

## Access the GraphQL endpoint:

   ```bash
   http://localhost:8080/graphql
   ```

### Default Admin Credentials:

On run a default admin user is created with the following credentials:
   - **Username:** admin
   - **Password:** admin

In the application, the email field is used as the username.

## GraphIQL Test Queries Sample

```graphql
query GetAllUsers{
    getAllUsers{
        id
        name
        email
        password
        role
    }
}

query GetUserById{
    getUserById(id:10){
        id
        name
        email
        password
        role
    }
}

query GetAllProducts{
    getAllProducts{
        id
        name
        stock
        price
    }
}

query GetProductById{
    getProductById(id:1){
        id
        name
        stock
        price
    }
}

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

query GetProductsByOrder{
    getProductsByOrder(id: 1) {
        id
        name
        stock
        price
    }
}

query GetAllOrdersByUser{
    getAllOrdersByUser(userId: 1) {
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

mutation CreateUser {
    createUser(userInput:{name:"Ernest", email:"e@gmail.com" password:"123", role:"ROLE_USER"}){
        id
        name
        email
        password
        role
    }
}

mutation CreateUser1 {
    createUser(userInput:{name:"Joojo", email:"j@gmail.com" password:"1234", role:"ROLE_USER"}){
        id
        name
        email
        password
        role
    }
}

mutation UpdateUser {
    updateUser(userInput:{id:1, name:"Admin", email:"e@gmail.com" password:"123", role:"ROLE_ADMIN"}){
        id
        name
        email
        password
        role
    }
}

mutation DeleteUser {
    deleteUser(id:1)
}

mutation CreateProduct {
    createProduct(productInput: {name: "Apple", stock: 10, price: 5.5}){
        id
        name
        stock
        price
    }
}
mutation CreateProduct1 {
    createProduct(productInput: {name: "Orange", stock: 30, price: 2.5}){
        id
        name
        stock
        price
    }
}

mutation CreateProduct2 {
    createProduct(productInput: {name: "Banana", stock: 15, price: 6.5}){
        id
        name
        stock
        price
    }
}

mutation UpdateProduct {
    updateProduct(productInput: {id: 1, name: "Apple", stock: 20, price: 5.5}){
        id
        name
        stock
        price
    }
}

mutation DeleteProduct {
    deleteProduct(id:1)
}

mutation CreateOrder{
    createOrder(orderInput: {
        productLineInputList: [
            { productId: 1, quantity: 3 },
            { productId: 2, quantity: 5 }
        ]
    }) {
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

mutation UpdateOrder{
    updateOrder(orderInput: {
        id: 1,
        productLineInputList: [
            { id: 1, productId: 1, quantity: 4 },
            { id: 3, productId: 3, quantity: 3 }
        ]
    }) {
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

mutation DeleteOrder {
    deleteOrder(id:1)
}

query GetLowStockProducts{
    getLowStockProducts(threshold:20){
        id
        name
        stock
        price
    }
}
```

## Additional Information

### Only Admins can :

- create and delete users
- create, update and delete products
- view all users and all orders

#### Only Users can:

- update their own details
- view, update and delete their own orders

#### Admins and Users can:

- create and update orders
- view all products
- view all low stock products