package com.example.cs102.Exceptions;

public class DuplicateCardException extends InvalidHandException{
    public DuplicateCardException(){
        super();
        message += "Hand contains duplicate choices";
    }
}
