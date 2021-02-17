package com.mickleentityltdnigeria.resturantapp.extensions;

import java.util.*;

@FunctionalInterface
public interface OrderDetailChangedHandler {
    void invoke(int orderDetailID);
}
