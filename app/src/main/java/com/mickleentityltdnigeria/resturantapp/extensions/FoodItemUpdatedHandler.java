package com.mickleentityltdnigeria.resturantapp.extensions;

import java.util.*;

@FunctionalInterface
public interface FoodItemUpdatedHandler
{
        void invoke(int foodID);
}
