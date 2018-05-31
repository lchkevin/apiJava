package com.apijava.apijava.service;

import com.apijava.apijava.domain.ApiInfo;

import java.util.List;

public interface ApiInfoService {

    void insert(ApiInfo apiInfo);
    void delete(ApiInfo apiInfo);
    void update(ApiInfo apiInfo);
    List<ApiInfo> getByCreateTime(String systemName);
}
