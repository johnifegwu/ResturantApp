package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.FeedBack;

import java.util.List;

@FunctionalInterface
public interface FeedBackEventHandler {
    void invoke(List<FeedBack> feedBackList);
}
