package com.mickleentityltdnigeria.resturantapp.exceptions;

public class NoNetworkException extends Exception{

    public NoNetworkException() {

        super("No available network connectivity.");
    }

    public NoNetworkException(String message) {

        super(message);
    }


}
