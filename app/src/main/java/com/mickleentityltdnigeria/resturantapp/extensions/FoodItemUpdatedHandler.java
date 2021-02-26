package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;

import java.util.*;

@FunctionalInterface
public interface FoodItemUpdatedHandler
{
        void invoke(List<FoodItem> foodID);
}
