package com.apijava.apijava.domain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ScanResult {
    public AtomicInteger total;
    public AtomicInteger scan;
    public AtomicInteger unScan;

    public ScanResult(AtomicInteger total, AtomicInteger scan, AtomicInteger unScan) {
        this.total = total;
        this.scan = scan;
        this.unScan = unScan;
    }
}
