package com.example.cs102.Exceptions;

public class InvalidHandException extends RuntimeException{
    protected String message;
    public InvalidHandException(String message){
        // message = "Please enter a valid hand\r\n";
        super("Please enter a valid hand\r\n");
    }
    // public String getMessage(){
    //     return message;
    // }
    public static void showValidChoices(){
        System.out.println("A valid input looks something like this");
        System.out.println("0 1 2 3 4 (5 cards)");
        System.out.println("4 7 (2 cards) ");
        System.out.println("8 (1 card)");
    }
}
