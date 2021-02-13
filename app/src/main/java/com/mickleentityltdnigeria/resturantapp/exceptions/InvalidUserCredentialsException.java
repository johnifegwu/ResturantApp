package com.mickleentityltdnigeria.resturantapp.exceptions;

public class InvalidUserCredentialsException extends Exception{

    public InvalidUserCredentialsException() {
        super("Invalid userName or Password");
    }
    public InvalidUserCredentialsException(String message) {
        super(message);
    }
}
