package com.apijava.apijava.domain;

import lombok.Data;

@Data
public class APIResult {
    public String id;
    public String url;
    public String responseBody;
    public String status_code;
    public String result;

    public APIResult(String id, String url, String responseBody, String status_code, String result) {
        this.id = id;
        this.url = url;
        this.responseBody = responseBody;
        this.status_code = status_code;
        this.result = result;
    }
}
