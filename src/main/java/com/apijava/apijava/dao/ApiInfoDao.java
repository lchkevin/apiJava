package com.apijava.apijava.dao;

import com.apijava.apijava.domain.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ApiInfoDao extends JpaRepository<ApiInfo, Long>, JpaSpecificationExecutor<ApiInfo> {
    List<ApiInfo> findAllBySystemName(String systemName);
    List<ApiInfo> findAllByPressFlagAndSystemName(boolean flag, String systemName);
}
