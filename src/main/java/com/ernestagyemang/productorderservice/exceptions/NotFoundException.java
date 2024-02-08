package com.ernestagyemang.productorderservice.exceptions;

import graphql.GraphQLException;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
