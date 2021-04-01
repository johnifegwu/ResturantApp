package com.mickleentityltdnigeria.resturantapp.utils;

public enum paymentResult{
    OK(1),
    FAILED(0),
    INSUFFICIENT_BALANCE(-1),
    CANCELED(-2),
    NETWORK_FAILURE(-3);

    private final int value;

    paymentResult(int i) {
        value = 1;
    }

    public int getValue(){
        return  value;
    }
}
