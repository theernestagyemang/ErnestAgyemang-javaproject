package com.ernestagyemang.productorderservice.exceptions;

public class Duplicate409Exception extends RuntimeException {
    public Duplicate409Exception(String message) {
        super(message);
    }
}
