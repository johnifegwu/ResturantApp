package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;

import java.util.List;

// Define delegate for components that are interested in User changed
@FunctionalInterface
public interface ResturantUpdatedHandler {
    void invoke(List<Resturant> Resturant);
}
