package com.apijava.apijava.domain;

import lombok.Data;

@Data
public class APIResult {
    public String id;
    public String url;
    public String body;
    public String result;

    public APIResult(String id, String url, String body, String result) {
        this.id = id;
        this.url = url;
        this.body = body;
        this.result = result;
    }
}
