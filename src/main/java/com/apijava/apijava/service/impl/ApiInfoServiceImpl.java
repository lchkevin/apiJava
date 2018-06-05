package com.apijava.apijava.service.impl;

import com.apijava.apijava.Utils.Tools;
import com.apijava.apijava.dao.ApiInfoDao;
import com.apijava.apijava.domain.ApiInfo;
import com.apijava.apijava.service.ApiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiInfoServiceImpl implements ApiInfoService {

    private final ApiInfoDao apiInfoDao;

    @Autowired
    public ApiInfoServiceImpl(ApiInfoDao apiInfoDao) {
        this.apiInfoDao = apiInfoDao;
    }

    @Override
    public void insert(ApiInfo apiInfo) {
        apiInfo.setEvm("SIT");
        apiInfo.setCreatTime((new Tools()).getTimeName());
        apiInfoDao.save(apiInfo);
    }

    @Override
    public void delete(ApiInfo apiInfo) {
        apiInfoDao.delete(apiInfo);
    }

    @Override
    public void update(ApiInfo apiInfo) {
        apiInfoDao.save(apiInfo);
    }

    @Override
    public List<ApiInfo> findAllBySystemName(String systemName) {
        return apiInfoDao.findAllBySystemName(systemName);
    }

    @Override
    public List<ApiInfo> findAllByPressFlagAndSystemName(boolean pressFlag, String systemName) {
        return apiInfoDao.findAllByPressFlagAndSystemName(pressFlag, systemName);
    }


}
