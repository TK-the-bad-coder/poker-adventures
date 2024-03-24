package com.example.cs102.Exceptions;

public class DuplicateCardException extends InvalidHandException{
    public DuplicateCardException(String message){
        super(message);
        // message += "Hand contains duplicate choices";
    }
}
