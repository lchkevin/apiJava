package com.apijava.apijava.domain;

import lombok.Data;

@Data
public class LearnResouce {
    public String id;
    public String url;
    public String body;
    public String result;

    public LearnResouce(String id, String url, String body, String result) {
        this.id = id;
        this.url = url;
        this.body = body;
        this.result = result;
    }
}
