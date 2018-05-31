package com.apijava.apijava.service;

import com.apijava.apijava.domain.ApiTestResult;

import java.util.List;

public interface ApiTestResultService {
    void save(ApiTestResult apiTestResult);
    void delete(ApiTestResult apiTestResult);
    void update(ApiTestResult apiTestResult);
    List<ApiTestResult> getByCreateTime(String systemName);
}
