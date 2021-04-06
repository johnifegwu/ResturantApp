package com.mickleentityltdnigeria.resturantapp.extensions;

import com.mickleentityltdnigeria.resturantapp.data.model.ApplicationData;

import java.util.List;

@FunctionalInterface
public interface ApplicationDataEvenHandler {

    void invoke(List<ApplicationData> applicationDataList);

}
