package com.apijava.apijava.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ApiInfo {
    @Id
    @GeneratedValue
    private Long id;
    private String evm;
    private String systemName;
    private String url;
    private String remark;
    private String httpMethod;
    private String header;
    private String setBody;
    private String expectResult;
    private String creatTime;
}
