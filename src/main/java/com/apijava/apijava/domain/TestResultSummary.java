package com.apijava.apijava.domain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class TestResultSummary {
    public AtomicInteger total;
    public AtomicInteger success;
    public AtomicInteger failed;

    public TestResultSummary(AtomicInteger total, AtomicInteger success, AtomicInteger failed) {
        this.total = total;
        this.success = success;
        this.failed = failed;
    }
}
