package com.mickleentityltdnigeria.resturantapp.extensions;

import java.util.*;

@FunctionalInterface
public interface OrderDetailChangedHandler {
    void invoke(String orderDetailID);
}
