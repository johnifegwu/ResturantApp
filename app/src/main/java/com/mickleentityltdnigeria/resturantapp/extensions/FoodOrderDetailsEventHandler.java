package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;

import java.util.List;

@FunctionalInterface
public interface FoodOrderDetailsEventHandler {
    void invoke(List<FoodOrderDetail> foodOrderDetails);
}
