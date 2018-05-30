package com.apijava.apijava.domain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ScanResult {
    public AtomicInteger total;
    public AtomicInteger success;
    public AtomicInteger failed;

    public ScanResult(AtomicInteger total, AtomicInteger success, AtomicInteger failed) {
        this.total = total;
        this.success = success;
        this.failed = failed;
    }
}
