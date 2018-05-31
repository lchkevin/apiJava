package com.apijava.apijava.dao;

import com.apijava.apijava.domain.ApiTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiTestResultDao extends JpaRepository<ApiTestResult, Long>, JpaSpecificationExecutor<ApiTestResult> {
}
