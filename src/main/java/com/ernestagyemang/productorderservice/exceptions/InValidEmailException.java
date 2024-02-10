package com.ernestagyemang.productorderservice.exceptions;

public class InValidEmailException extends RuntimeException {
    public InValidEmailException(String message) {
        super(message);
    }
}
