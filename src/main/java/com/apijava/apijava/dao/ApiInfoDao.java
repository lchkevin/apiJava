package com.apijava.apijava.dao;

import com.apijava.apijava.domain.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiInfoDao extends JpaRepository<ApiInfo, Long>, JpaSpecificationExecutor<ApiInfo> {
}
