package com.apijava.apijava.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "apiinfo",
uniqueConstraints = {@UniqueConstraint(columnNames = "gatlingTestName")})
public class ApiInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String evm;
    private String systemName;
    private String baseUrl;
    private String uri;
    private String remark;
    private String httpMethod;
    private String header;
    private String setBody;
    private String expectResult;
    private String creatTime;
    private Boolean pressFlag;
    @Column(unique = true,length = 100)
    private String gatlingTestName;
    @Column()
    private int status_code;
}
