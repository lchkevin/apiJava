package com.apijava.apijava.dao;

import com.apijava.apijava.domain.ApiTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ApiTestResultDao extends JpaRepository<ApiTestResult, Long>, JpaSpecificationExecutor<ApiTestResult> {
    List<ApiTestResult> findAllByCreateTime(String time);
    List<ApiTestResult> findAllByCreateTimeAfterAndSystemNameEquals(String time, String systemName);
}
