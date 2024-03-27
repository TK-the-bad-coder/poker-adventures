package com.example.cs102.exceptions;

public class InvalidHandException extends RuntimeException {

    public InvalidHandException(String message) {
        super(message);
    }

    public static void showValidChoices() {
        System.out.println("A valid input looks something like this");
        System.out.println("0 1 2 3 4 (5 cards)");
        System.out.println("4 7 (2 cards) ");
        System.out.println("8 (1 card)");
    }
}
