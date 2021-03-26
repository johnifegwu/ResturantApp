package com.mickleentityltdnigeria.resturantapp.dalc;

public class Dalc {

    public static UserDalc User() {
        //
        return new UserDalc();
        //
    }

    public static AddressDalc Address() {
        //
        return new AddressDalc();
        //
    }

    public static CartDalc Cart() {
        //
        return new CartDalc();
        //
    }

    public static FoodItemDalc Food() {
        //
        return new FoodItemDalc();
        //
    }

    public static ResturantDalc Resturant(){
        //
        return new ResturantDalc();
        //
    }

    public static FoodOrderDalc Order(){
        return new FoodOrderDalc();
    }

}
