package com.apijava.apijava.domain;

import lombok.Data;

@Data
public class TestResultSummary {
    public String total;
    public String success;
    public String failed;

    public TestResultSummary(String total, String success, String failed) {
        this.total = total;
        this.success = success;
        this.failed = failed;
    }
}
