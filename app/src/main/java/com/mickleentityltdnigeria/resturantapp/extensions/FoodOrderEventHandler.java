package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;

import java.util.List;

@FunctionalInterface
public interface FoodOrderEventHandler {
    void invoke(List<FoodOrder> foodOrders);
}
