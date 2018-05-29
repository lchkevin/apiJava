package com.apijava.apijava.domain;

import lombok.Data;

@Data
public class ResponseEntity {
    public String code;
    public String responseBody;

    public ResponseEntity(String code, String responseBody) {
        this.code = code;
        this.responseBody = responseBody;
    }
}
