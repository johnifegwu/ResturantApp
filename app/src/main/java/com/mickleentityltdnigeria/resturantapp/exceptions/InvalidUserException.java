package com.mickleentityltdnigeria.resturantapp.exceptions;

public class InvalidUserException extends Exception{

    public InvalidUserException() {
        super("Invalid user type or credentials");
    }
    public InvalidUserException(String message) {
        super(message);
    }
}
