package com.apijava.apijava.service.impl;

import com.apijava.apijava.Utils.Tools;
import com.apijava.apijava.dao.ApiTestResultDao;
import com.apijava.apijava.domain.ApiTestResult;
import com.apijava.apijava.service.ApiTestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiTestResultServiceImpl implements ApiTestResultService {

    private final ApiTestResultDao apiTestResultDao;

    @Autowired
    public ApiTestResultServiceImpl(ApiTestResultDao apiTestResultDao) {
        this.apiTestResultDao = apiTestResultDao;
    }
    

    @Override
    public void insert(ApiTestResult apiTestResult) {
        apiTestResult.setCreateTime((new Tools()).getTimeName());
        apiTestResultDao.save(apiTestResult);
    }

    @Override
    public void delete(ApiTestResult apiTestResult) {
        apiTestResultDao.delete(apiTestResult);
    }

    @Override
    public void update(ApiTestResult apiTestResult) {
        apiTestResultDao.save(apiTestResult);
    }

    @Override
    public List<ApiTestResult> getByCreateTime(String systemName) {
        return null;
    }
}
