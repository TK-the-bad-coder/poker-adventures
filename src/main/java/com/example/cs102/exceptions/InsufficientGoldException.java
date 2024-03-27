package com.example.cs102.exceptions;

public class InsufficientGoldException extends RuntimeException {

    public InsufficientGoldException(String msg) {
        super(msg);
    }
}
