package com.apijava.apijava.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ApiTestResult {
    @Id
    @GeneratedValue
    private Long id;
    private String systemName;
    private String evm;
    private String url;
    private String httpMethod;
    private String header;
    private String setBody;
    private String expectResult;
    private String verification;
    private int status_code;
    private String responseBody;
    private String createTime;
}
